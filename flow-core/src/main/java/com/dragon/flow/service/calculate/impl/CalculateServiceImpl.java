package com.dragon.flow.service.calculate.impl;

import com.dragon.flow.config.modeler.KieUtilClass;
import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.flow.model.calculate.RegionInstanceModel;
import com.dragon.flow.model.calculate.RegionModel;
import com.dragon.flow.service.calculate.*;
import com.dragon.flow.vo.calculate.CalculateParamVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.beanutils.BeanUtils;
import org.bson.Document;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class CalculateServiceImpl implements CalculateService {

    @Autowired
    private KieUtilClass kieUtilClass;

    @Autowired
    private ModelsRepository modelsRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private RegionInstanceRepository regionInstanceRepository;

    @Override
    public CalculateModel getModelById(String id) {
        CalculateModel calculateModel = modelsRepository.findById(id).get();
        List<RegionModel> info = regionRepository.findByModelId(id);
        calculateModel.setTemplate(info);
        return calculateModel;
    }

    @Override
    @Transactional
    public InstanceModel newInstance(InstanceModel instanceModel) throws Exception{
        String modelId = instanceModel.getModelId();
        InstanceModel save = instanceRepository.save(instanceModel);
        String id = save.getId();
        CalculateModel calculateModel = this.getModelById(modelId);
        List<RegionModel> template = calculateModel.getTemplate();
        Map<String, Class<?>> classMap = kieUtilClass.getClassMap();
        List<RegionInstanceModel> regionInstanceModels = new ArrayList<>();
        template.forEach(item->{
            RegionInstanceModel regionInstanceModel = new RegionInstanceModel();
            regionInstanceModel.setInstanceId(id);
            regionInstanceModel.setRegionId(item.getId());
            regionInstanceModel.setClassName(item.getInfo().getClassName());
            regionInstanceModel.setRelationIn(item.getRelationIn());
            regionInstanceModel.setRelationOut(item.getRelationOut());
            regionInstanceModel.setDescription(item.getInfo().getDescription());
            try {
                Class<?> aClass = classMap.get(item.getInfo().getClassName());
                Object data = aClass.newInstance();
                Document document = new Document();
                for (Field field : aClass.getDeclaredFields()) {
                    field.setAccessible(true); // 强制访问私有字段
                    Object value = field.get(data);
                    document.put(field.getName(), value);
                }
                regionInstanceModel.setData(document);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            regionInstanceModels.add(regionInstanceModel);
        });
        List<RegionInstanceModel> regionInstanceModelSave = regionInstanceRepository.saveAll(regionInstanceModels);
        save.setData(regionInstanceModelSave);
        return save;
    }

    @Override
    public Object getCalculateInstance(CalculateParamVo param) throws Exception {
        Object data = param.getParam();
        String fullName = param.getTypeName();
        //使用regionId和modelId套用模板
        String regionId = param.getRegionId();
        String modelId = param.getModelId();
        String regionInstanceId = param.getRegionInstanceId();
        RegionModel regionModel = new RegionModel();
        regionModel.setModelId(modelId);
        regionModel.setId(regionId);
        Example<RegionModel> example = Example.of(regionModel);
        //得到了模型中该计算域的信息，包括relationIn和Out
        RegionModel region = regionRepository.findOne(example).get();
        Map<String, Class<?>> clazzMap = kieUtilClass.getClassMap();
        KieContainer kieContainer = kieUtilClass.getKieContainer();
        KieSession kieSession = kieContainer.getKieBase().newKieSession();
        //创建实例
        Object instance = clazzMap.get(fullName).newInstance();
        //从Object接收的param中取出付给新建的实例
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.valueToTree(data);
        //用于记录是否达到计算的条件
        // 1是数据是否发生变化
        RegionInstanceModel regionInstanceModelFromMongo = regionInstanceRepository.findById(regionInstanceId).get();
        Document dataFromMongo = regionInstanceModelFromMongo.getData();
        boolean flag = true;
        objectNode.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": " + fieldValue);
            this.callSetterMethod(instance,fieldName,fieldValue);
        });
        //插入带值的实例
        kieSession.insert(instance);
        kieSession.fireAllRules();
        kieSession.dispose();
        //将结果存入数据库
        try {
            Class<?> aClass = clazzMap.get(fullName);
            Document document = new Document();
            for (Field field : aClass.getDeclaredFields()) {
                field.setAccessible(true); // 强制访问私有字段
                Object value = field.get(instance);
                document.put(field.getName(), value);
            }
            regionInstanceModelFromMongo.setData(document);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //还需要使用乐观锁处理线程安全问题

        regionInstanceRepository.save(regionInstanceModelFromMongo);
        ObjectMapper objectMapper1 = new ObjectMapper();
        ObjectNode objectNode1 = objectMapper1.valueToTree(instance);
        objectNode1.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": :::::" + fieldValue);
        });
        return instance;
    }

    @Override
    public  void callSetterMethod(Object obj, String propertyName,Object fieldValue) {
        // 获取对象的 Class 对象
        try {
            BeanUtils.setProperty(obj, propertyName, fieldValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void printKieFileSystemContents(KieFileSystem kieFileSystem) {
        try {
            Field field = kieFileSystem.getClass().getDeclaredField("files");
            field.setAccessible(true);
            Map<String, byte[]> files = (Map<String, byte[]>) field.get(kieFileSystem);
            System.out.println("===== KieFileSystem 中的文件列表 =====");
            files.forEach((path, content) -> {
                System.out.println("路径: " + path + ", 大小: " + content.length + " 字节");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
