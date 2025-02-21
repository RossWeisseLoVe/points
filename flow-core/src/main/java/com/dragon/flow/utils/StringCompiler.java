package com.dragon.flow.utils;

import com.itranswarp.compiler.JavaStringCompiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class StringCompiler {
    public static Object run(String source, String className, String packageName) throws Exception {
        String fullName = String.format("%s.%s", packageName, className);
        System.out.println("1222222   "+fullName);

        // 编译器
        JavaStringCompiler compiler = new JavaStringCompiler();
        // 编译：compiler.compile("Main.java", source)
        Map<String, byte[]> results = compiler.compile(className + ".java",  source);
        // 加载内存中byte到Class<?>对象
        Class<?> clazz = compiler.loadClass(fullName, results);
        // 创建实例
        Object instance = clazz.newInstance();

//        saveBytecodeToFile(results.get(fullName),"com/dragon/flow/model/test/Calculate");


//         保存类字节码到文件系统
//        Map<String, byte[]> results2 = compiler.compile("com/dragon/flow/model/test/Calculate.java", source);
        String classFileName = fullName.replace('.', File.separatorChar) + ".class";
        File classFile = new File("target/classes", classFileName);
        classFile.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(classFile)) {
            fos.write(results.get(fullName));
        }

        return instance;
    }

    private static void saveBytecodeToFile(byte[] bytecode, String className) {
        System.out.println("7777777777");
        System.out.println("target/classes/" + className.replace('.', '/') + ".class");
        File outputFile = new File("target/classes/" + className.replace('.', '/') + ".class");
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(bytecode);
            System.out.println("Class file saved to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}