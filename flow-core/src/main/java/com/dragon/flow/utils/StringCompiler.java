package com.dragon.flow.utils;

import com.itranswarp.compiler.JavaStringCompiler;

import java.util.Map;

public class StringCompiler {
    public static Object run(String source, String className, String packageName) throws Exception {
        // 声明包名：package top.fomeiherz;
        System.out.println("1222222   "+packageName);
//        String prefix = String.format("package %s;", packageName);
        // 全类名：top.fomeiherz.Main
        String fullName = String.format("%s.%s", packageName, className);

        // 编译器
        JavaStringCompiler compiler = new JavaStringCompiler();
        // 编译：compiler.compile("Main.java", source)
        Map<String, byte[]> results = compiler.compile(className + ".java",  source);
        // 加载内存中byte到Class<?>对象
        Class<?> clazz = compiler.loadClass(fullName, results);
        // 创建实例
        Object instance = clazz.newInstance();
        return instance;
    }
}