package com.dragon.flow.web.resource.test;

import com.dragon.flow.config.modeler.KieUtilClass;
import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.beanutils.BeanUtils;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RestController
@RequestMapping("/flow/test/calculate")
public class CalculateResource {

    @Autowired
    private KieUtilClass kieUtilClass;


    @PostMapping("getAverage")
    public ReturnVo<Object> page(@RequestBody Object param,@RequestParam String typeName) throws Exception {
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
        ReturnVo<Object> calculateReturnVo = new ReturnVo<>();
        calculateReturnVo.setCode(ReturnCode.SUCCESS);
        calculateReturnVo.setData(instance);
        calculateReturnVo.setMsg("计算成功");
        return calculateReturnVo;
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
