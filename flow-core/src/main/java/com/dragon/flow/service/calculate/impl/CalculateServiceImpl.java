package com.dragon.flow.service.calculate.impl;

import com.dragon.flow.config.modeler.KieUtilClass;
import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.flow.model.calculate.RegionInstanceModel;
import com.dragon.flow.model.calculate.RegionModel;
import com.dragon.flow.service.calculate.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.beanutils.BeanUtils;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
            regionInstanceModel.setClassName(item.getInfo().getClassName());
            regionInstanceModel.setRelationIn(item.getRelationIn());
            regionInstanceModel.setRelationOut(item.getRelationOut());
            try {
                Object data = classMap.get(item.getInfo().getClassName()).newInstance();
                regionInstanceModel.setData(data);
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
    public Object getCalculateInstance(Object param, String typeName) throws Exception {
        String packageName = "com.dragon.flow.model.test";  //默认包名
        String fullName = String.format("%s.%s", packageName, typeName);  //全类名
        Map<String, Class<?>> clazzMap = kieUtilClass.getClassMap();
        KieContainer kieContainer = kieUtilClass.getKieContainer();
        KieSession kieSession = kieContainer.getKieBase().newKieSession();
        kieSession.addEventListener(new DebugRuleRuntimeEventListener() {
            @Override
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("插入对象: " + event.getObject().toString());
            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("更新对象: " + event.getObject());
            }

        });

        kieSession.addEventListener(new AgendaEventListener() {

            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("触发的规则: " + event.getMatch().getRule().getName());
            }
            @Override
            public void matchCreated(MatchCreatedEvent event) {}
            @Override
            public void matchCancelled(MatchCancelledEvent event) {}
            @Override
            public void beforeMatchFired(BeforeMatchFiredEvent event) {}
            @Override
            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {}
            @Override
            public void agendaGroupPushed(AgendaGroupPushedEvent event) {}
            @Override
            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {}
            @Override
            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {}

            @Override
            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {

            }

            @Override
            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {

            }
        });

        //创建实例

        System.out.println(clazzMap.get(fullName).getClassLoader());
        Object instance = clazzMap.get(fullName).newInstance();
        //从Object接收的param中取出付给新建的实例
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.valueToTree(param);
        objectNode.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": " + fieldValue);
            callSetterMethod(instance,fieldName,fieldValue);
        });
        //插入带值的实例
        kieSession.insert(instance);
        int i = kieSession.fireAllRules();
        Collection<FactHandle> factHandles = kieSession.getFactHandles();
        for (FactHandle element : factHandles) {
            System.out.println(element.toString());
        }

        System.out.println("触发规则条数："+i);
        kieSession.dispose();
        ObjectMapper objectMapper1 = new ObjectMapper();
        ObjectNode objectNode1 = objectMapper1.valueToTree(instance);
        objectNode1.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": :::::" + fieldValue);
        });
        return instance;
    }


    public static void callSetterMethod(Object obj, String propertyName,Object fieldValue) {
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
