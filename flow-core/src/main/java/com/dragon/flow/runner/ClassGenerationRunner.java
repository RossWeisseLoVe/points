package com.dragon.flow.runner;

import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.utils.JavaSourceGenerator;
import com.dragon.flow.utils.StringCompiler;
import org.reflections.Reflections;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ClassGenerationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 创建类定义信息
        ClassDefinition classDefinition = new ClassDefinition();
        String packageName = "com.dragon.flow.model.test";
        String simpleClassName = "DynamicGeneratedClass";
        classDefinition.setClassName(packageName + "." + simpleClassName);

        List<PropertyDefinition> properties = new ArrayList<>();
        PropertyDefinition property1 = new PropertyDefinition();
        property1.setPropertyName("name");
        property1.setPropertyType("String");
        properties.add(property1);

        PropertyDefinition property2 = new PropertyDefinition();
        property2.setPropertyName("age");
        property2.setPropertyType("int");
        properties.add(property2);

        classDefinition.setProperties(properties);

        // 生成 Java 源代码
        String sourceCode = JavaSourceGenerator.generateSourceCode(classDefinition);
        System.out.println("sourceCode:"+ sourceCode);
        // 使用 StringCompiler 编译并加载类
        Object instance = StringCompiler.run(sourceCode, simpleClassName, packageName);

//        // 类扫描
//        Reflections reflections = new Reflections(packageName);
//        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
//        System.out.println("Classes in package " + packageName + ":");
//        for (Class<?> clazz : classes) {
//            System.out.println("heihei"+clazz.getName());
//        }
    }

}