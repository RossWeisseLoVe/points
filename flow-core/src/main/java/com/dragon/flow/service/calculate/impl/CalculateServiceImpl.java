package com.dragon.flow.service.calculate.impl;

import com.dragon.flow.config.modeler.KieUtilClass;
import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.flow.model.calculate.RegionInstanceModel;
import com.dragon.flow.model.calculate.RegionModel;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.service.calculate.*;
import com.dragon.flow.vo.calculate.CalculateParamVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.beanutils.BeanUtils;
import org.bson.Document;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    public Object getCalculateInstance(CalculateParamVo param , boolean isFromWeb) throws Exception {
        System.out.println("====================================="+param.getTypeName());
        Object data = param.getParam();
        String fullName = param.getTypeName();
        //使用regionId和modelId套用模板
        String regionId = param.getRegionId();
        String modelId = param.getModelId();
        String instanceId = param.getInstanceId();
        RegionModel regionModel = new RegionModel();
        regionModel.setModelId(modelId);
        regionModel.setId(regionId);
        Example<RegionModel> example = Example.of(regionModel);
        //得到了模型中该计算域的信息，包括relationIn和Out
        RegionModel region = regionRepository.findOne(example).get();
        List<PropertyDefinition> properties = region.getInfo().getProperties();
        //需要提供信息给对方的邻居节点
        Document relationOut = region.getRelationOut();
        //动态类map
        Map<String, Class<?>> clazzMap = kieUtilClass.getClassMap();
        KieContainer kieContainer = kieUtilClass.getKieContainer();
        KieSession kieSession = kieContainer.getKieBase().newKieSession();
        //创建实例
        Object instance = clazzMap.get(fullName).newInstance();
        Class<?> regionClass = clazzMap.get(fullName);
        //从Object接收的param中取出付给新建的实例
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectNode objectNode = objectMapper.valueToTree(data);
        //用于记录是否达到计算的条件，默认不能计算
        AtomicBoolean flag = new AtomicBoolean(false);
        String regionInstanceId = param.getRegionInstanceId();
        if(isFromWeb){
            //从客户端传来的请求
            RegionInstanceModel regionInstanceModelFromMongo = regionInstanceRepository.findById(regionInstanceId).get();
            Document dataFromMongo = regionInstanceModelFromMongo.getData();
            objectNode.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                try {
                    Class<?> aClass = regionClass.getDeclaredField(fieldName).getType();
                    Object cast = aClass.cast(dataFromMongo.get(fieldName));
                    if(cast==null&&fieldValue!=null){
                        flag.set(true);
                    }else if (cast!=null&&!cast.equals(objectMapper.treeToValue((TreeNode) fieldValue,aClass))){
                        //如果数据发生改变即可计算
                        flag.set(true);
                    }
                    System.out.println(fieldName + ": " + fieldValue + ":::" +cast);
                    this.callSetterMethod(instance,fieldName,fieldValue);
                } catch (NoSuchFieldException | JsonProcessingException e) {
                    e.printStackTrace();
                }
            });

        }else{
            //程序自己异步调用
            RegionInstanceModel regionInstanceModel = new RegionInstanceModel();
            regionInstanceModel.setInstanceId(instanceId);
            regionInstanceModel.setRegionId(regionId);
            Example<RegionInstanceModel> regionInstanceModelExampleexample = Example.of(regionInstanceModel);
            RegionInstanceModel regionInstanceModelFromMongo = regionInstanceRepository.findOne(regionInstanceModelExampleexample).get();
            regionInstanceId = regionInstanceModelFromMongo.getId();
            Document dataFromMongo = regionInstanceModelFromMongo.getData();
            for (Map.Entry<String, Object> entry : dataFromMongo.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                if(fieldValue==null){
                    continue;
                }
                Class<?> aClass = entry.getValue().getClass();
                Object cast = aClass.cast(dataFromMongo.get(fieldName));
                if(fieldValue==null&&cast!=null){
                    //传入的值如果非空就赋给该属性
                    this.callSetterMethod(instance,fieldName,cast);
                    System.out.println("写入值为cast："+cast);
                }else{
                    this.callSetterMethod(instance,fieldName,fieldValue);
                    System.out.println("写入值为fieldValue："+fieldValue);
                }
                System.out.println(fieldName + ": " + fieldValue + ":::" +cast);
            }
            Document autoData = (Document) param.getParam();
            //将传入的值赋给instance
            for (Map.Entry<String, Object> entry : autoData.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                Class<?> aClass = entry.getValue().getClass();
                Object cast = aClass.cast(dataFromMongo.get(fieldName));
                if(fieldValue!=null){
                    //传入的值如果非空就赋给该属性
                    this.callSetterMethod(instance,fieldName,fieldValue);
                    flag.set(true);
                    System.out.println("传入的值");
                }
                System.out.println(fieldName + ": " + fieldValue + ":::" +cast);
            }
        }
        ObjectNode objectNode1 = objectMapper.valueToTree(instance);
        objectNode1.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": :::::" + fieldValue+ ": :::::" + fieldValue.getClass());
        });
        if(flag.get()==false){
            return null;
        }
        //插入带值的实例
        kieSession.insert(instance);
        kieSession.fireAllRules();
        kieSession.dispose();
        RegionInstanceModel regionInstanceModel = new RegionInstanceModel();
        //将结果存入数据库
        try {
            Document document = new Document();
            for (Field field : regionClass.getDeclaredFields()) {
                field.setAccessible(true); // 强制访问私有字段
                Object value = field.get(instance);
//                System.out.println("value-------------"+value+"-------"+value.getClass());
                document.put(field.getName(), value);
            }
            regionInstanceModel.setData(document);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //还需要使用乐观锁处理线程安全问题
        regionInstanceModel.setId(regionInstanceId);
        regionInstanceModel.setInstanceId(instanceId);
        regionInstanceModel.setRegionId(regionId);
        regionInstanceRepository.save(regionInstanceModel);

        final Lock lock = new ReentrantLock();
        if(relationOut!=null){
            for (Map.Entry<String, Object> entry : relationOut.entrySet()) {
                String key = entry.getKey();
                List<Document> value = (List<Document>) entry.getValue();
                value.forEach(item->{
                    Object dataValue = regionInstanceModel.getData().get(key);
                    if(dataValue==null){
                        return;
                    }
                    System.out.println("Key: " + key + ", Value: " + item.toString());
                    String targetObjId = (String) item.get("targetObjId");
                    String targetPropertyName = (String) item.get("targetPropertyName");
                    CalculateParamVo calculateParamVo = new CalculateParamVo();
                    calculateParamVo.setRegionId(targetObjId);
                    calculateParamVo.setInstanceId(instanceId);
                    calculateParamVo.setModelId(modelId);
                    Document document = new Document();
                    document.put(targetPropertyName,dataValue);
                    String className = regionRepository.findById(targetObjId).get().getInfo().getClassName();
                    calculateParamVo.setParam(document);
                    calculateParamVo.setTypeName(className);
                    //异步调用
                    CompletableFuture.runAsync(()->{
                        //此处会有线程安全问题，需要解决
                        lock.lock();
                        try {
                            this.getCalculateInstance(calculateParamVo,false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            lock.unlock();
                        }
                    });
                });
            }
        }
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

}
