package com.dragon.flow.utils;
import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;

import java.util.List;

public class JavaSourceGenerator {

    public static String generateSourceCode(ClassDefinition classDefinition) {
        StringBuilder sourceCode = new StringBuilder();
        String packageName = classDefinition.getClassName().substring(0, classDefinition.getClassName().lastIndexOf("."));
        String simpleClassName = classDefinition.getClassName().substring(classDefinition.getClassName().lastIndexOf(".") + 1);

        // 包声明
        sourceCode.append("package ").append(packageName).append(";\n\n");

        // 类声明
        sourceCode.append("public class ").append(simpleClassName).append(" {\n\n");

        // 属性声明
        List<PropertyDefinition> properties = classDefinition.getProperties();
        for (PropertyDefinition property : properties) {
            sourceCode.append("    private ").append(property.getPropertyType()).append(" ").append(property.getPropertyName()).append(";\n");
        }

        sourceCode.append("\n");

        // getter 和 setter 方法
        for (PropertyDefinition property : properties) {
            String capitalizedPropertyName = capitalize(property.getPropertyName());
            // getter 方法
            sourceCode.append("    public ").append(property.getPropertyType()).append(" get").append(capitalizedPropertyName).append("() {\n");
            sourceCode.append("        return ").append(property.getPropertyName()).append(";\n");
            sourceCode.append("    }\n\n");
            // setter 方法
            sourceCode.append("    public void set").append(capitalizedPropertyName).append("(").append(property.getPropertyType()).append(" ").append(property.getPropertyName()).append(") {\n");
            sourceCode.append("        this.").append(property.getPropertyName()).append(" = ").append(property.getPropertyName()).append(";\n");
            sourceCode.append("    }\n\n");
        }

        // 类结束
        sourceCode.append("}");

        return sourceCode.toString();
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}