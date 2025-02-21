package com.dragon.flow.web.resource.test;

import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.service.test.CalculateService;
import com.dragon.flow.utils.DynamicClassLoader;
import com.dragon.flow.utils.JavaSourceGenerator;
import com.dragon.flow.utils.StringCompiler;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itranswarp.compiler.JavaStringCompiler;
import org.apache.commons.beanutils.BeanUtils;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.modelcompiler.builder.KieBaseBuilder;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.core.util.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flow/test/calculate")
public class CalculateResource {

    @Autowired
    private CalculateService calculateService;

    @PostMapping("getAverage")
    public ReturnVo<Object> page(@RequestBody Object param,@RequestParam String typeName) throws Exception {
        ClassDefinition classDefinition = new ClassDefinition();
        String packageName = "com.dragon.flow.model.test";
        String simpleClassName = "Calculate";
        String fullName = String.format("%s.%s", packageName, simpleClassName);
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

        // 生成 Java 源代码
        String sourceCode = JavaSourceGenerator.generateSourceCode(classDefinition);
        System.out.println("sourceCode:"+ sourceCode);

        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, byte[]> results = compiler.compile((fullName).replace('.', '/') + ".java", sourceCode);
//        DynamicClassLoader classLoader = new DynamicClassLoader(results, Thread.currentThread().getContextClassLoader());
//        Class<?> calculateClass = classLoader.loadClass(fullName);

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        String classFilePath = "src/main/resources/" + fullName.replace('.', '/') + ".class";
        kieFileSystem.write(classFilePath,results.get(fullName));



        SpreadsheetCompiler compilerExcel = new SpreadsheetCompiler();
        String drl = compilerExcel.compile(new FileInputStream("E:/workspace/points/flow-master/flow-core/src/main/resources/rules/rule.xls"), InputType.XLS);
        kieFileSystem.write("src/main/resources/rules.drl", drl);
        System.out.println("DRL______________"+drl);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        Results resultsBuilder = kieBuilder.getResults();
        if (resultsBuilder.hasMessages(Message.Level.ERROR)) {
            System.err.println(resultsBuilder.getMessages());
            throw new RuntimeException("Build Errors: " + resultsBuilder.getMessages());
        }

        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();

        // 6. 创建 KieSession 并执行规则
        KieSession kieSession = kieBase.newKieSession();
//        KieServices kieServices = KieServices.Factory.get();
//            //获取kieContainer
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//        kieFileSystem.write("classpath:"+packageName.replace(".","/"),)
//        KieContainer kieContainer = kieServices.getKieClasspathContainer();
//        KieSession kieSession = kieContainer.newKieSession("simpleRuleKSession");
        Object instance = StringCompiler.run(sourceCode, simpleClassName, packageName);
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

        kieSession.insert(instance);
        kieSession.fireAllRules();
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


}
