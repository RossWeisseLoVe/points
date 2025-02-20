package com.dragon.flow.web.resource.test;

import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.model.test.*;
import com.dragon.flow.service.test.CalculateService;
import com.dragon.flow.utils.JavaSourceGenerator;
import com.dragon.flow.utils.StringCompiler;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
        classDefinition.setClassName(packageName + "." + simpleClassName);
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
        // 使用 StringCompiler 编译并加载类
        Object instance = StringCompiler.run(sourceCode, simpleClassName, packageName);
        Class<?> clazz = param.getClass();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.valueToTree(param);
        objectNode.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": " + fieldValue);
            try {
                callSetterMethod(instance,"set"+ Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1),fieldName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        ObjectMapper objectMapper1 = new ObjectMapper();
        ObjectNode objectNode1 = objectMapper1.valueToTree(instance);
        objectNode1.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            System.out.println(fieldName + ": :::::" + fieldValue);
        });
        System.out.println("看看"+param.toString()+typeName);
        KieServices kieServices = KieServices.Factory.get();
            //获取kieContainer
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("simpleRuleKSession");
        kieSession.insert(instance);
        kieSession.fireAllRules();
        kieSession.dispose();
        ReturnVo<Object> calculateReturnVo = new ReturnVo<>();
        calculateReturnVo.setCode(ReturnCode.SUCCESS);
        calculateReturnVo.setData(param);
        calculateReturnVo.setMsg("计算成功");
        return calculateReturnVo;
    }

    public static void callSetterMethod(Object obj, String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 获取对象的 Class 对象
        Class<?> clazz = obj.getClass();

        // 获取参数类型数组
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }

        System.out.println(methodName+"~~~~~~~"+parameterTypes[0].toString());
        // 获取 setter 方法
        Method setterMethod = clazz.getMethod(methodName, parameterTypes);


        // 调用 setter 方法
        setterMethod.invoke(obj, args);
    }


}
