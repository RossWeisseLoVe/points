package com.dragon.flow.web.resource.test;

import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.service.test.CalculateService;
import com.dragon.flow.utils.JavaSourceGenerator;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itranswarp.compiler.JavaStringCompiler;
import org.apache.commons.beanutils.BeanUtils;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RestController
@RequestMapping("/flow/test/calculate")
public class CalculateResource {

    @Autowired
    private CalculateService calculateService;

    @PostMapping("getAverage")
    public ReturnVo<Object> page(@RequestBody Object param,@RequestParam String typeName) throws Exception {
        ClassDefinition classDefinition = new ClassDefinition();  //该类用于生成动态类
        String packageName = "com.dragon.flow.model.test";  //默认包名
        String simpleClassName = "Calculate";  //类名，从url上获取typeName
        String fullName = String.format("%s.%s", packageName, simpleClassName);  //全类名
        //以下步骤需要自动化实现
        classDefinition.setClassName(fullName);
        List<PropertyDefinition> properties = new ArrayList<>();
        PropertyDefinition property1 = new PropertyDefinition();
        property1.setPropertyName("first");
        property1.setPropertyType("Double");
        properties.add(property1);

        PropertyDefinition property2 = new PropertyDefinition();
        property2.setPropertyName("second");
        property2.setPropertyType("Double");
        properties.add(property2);

        PropertyDefinition property3 = new PropertyDefinition();
        property3.setPropertyName("third");
        property3.setPropertyType("Double");
        properties.add(property3);

        PropertyDefinition property4 = new PropertyDefinition();
        property4.setPropertyName("average");
        property4.setPropertyType("Double");
        properties.add(property4);

        PropertyDefinition property5 = new PropertyDefinition();
        property5.setPropertyName("errorMsg");
        property5.setPropertyType("String");
        properties.add(property5);
        classDefinition.setProperties(properties);

        // 生成 Java 源代码 包括getter setter toString
        String sourceCode = JavaSourceGenerator.generateSourceCode(classDefinition);
        System.out.println("sourceCode:"+ sourceCode);
        //将生成的java代码编译为字节码
        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, byte[]> results = compiler.compile((fullName).replace('.', '/') + ".java", sourceCode);

        //初始化Drools
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        //将生成的动态类写入Drools的文件系统
        String classFilePath = "src/main/resources/" + fullName.replace('.', '/') + ".class";
        kieFileSystem.write(classFilePath,results.get(fullName));
        //将excel的规则编译成drl并写入kfs
        SpreadsheetCompiler compilerExcel = new SpreadsheetCompiler();
        String drl = compilerExcel.compile(new FileInputStream("E:/workspace/points/flow-master/flow-core/src/main/resources/rules/rule.xls"), InputType.XLS);
        kieFileSystem.write("src/main/resources/rules.drl", drl);
        //直接使用excel的方法
//        kieFileSystem.write(
//                ResourceFactory.newClassPathResource("rules/rule.xls")
//                .setResourceType(ResourceType.DTABLE)
//                .setSourcePath("src/main/resources/rules/rule.xlsx")
//        );
        System.out.println("DRL______________"+drl);
        //构建
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        Results resultsBuilder = kieBuilder.getResults();
        System.out.println("Messages:"+resultsBuilder.getMessages());
        //输出错误信息
        if (resultsBuilder.hasMessages(Message.Level.ERROR)) {
            System.err.println(resultsBuilder.getMessages());
            throw new RuntimeException("Build Errors: " + resultsBuilder.getMessages());
        }
        //根据字节码与全类名创建动态类（jvm）
        Class<?> clazz = compiler.loadClass(fullName, results);
        //KieContainer使用的类加载器与创建的类一致，否则无法触发规则
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId(),clazz.getClassLoader());
        KieBase kieBase = kieContainer.getKieBase();
        KieSession kieSession = kieBase.newKieSession();
        //监听，后期可注释
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

        // 创建实例
        Object instance = clazz.newInstance();
        //从Object接收的param中取出付给新建的实例
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.valueToTree(param);
        objectNode.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": " + fieldValue);
            callSetterMethod(instance,fieldName,fieldValue);
        });
        ObjectMapper objectMapper1 = new ObjectMapper();
        ObjectNode objectNode1 = objectMapper1.valueToTree(instance);
        objectNode1.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": :::::" + fieldValue);
        });
        //插入带值的实例
        kieSession.insert(instance);
        int i = kieSession.fireAllRules();
        System.out.println("触发规则条数："+i);
        kieSession.dispose();
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
