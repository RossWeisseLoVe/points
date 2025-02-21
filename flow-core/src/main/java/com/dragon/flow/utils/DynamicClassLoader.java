package com.dragon.flow.utils;


import java.util.Map;

public class DynamicClassLoader extends ClassLoader {
//    private final byte[] classBytes;
//
//    public DynamicClassLoader(Map<String, byte[]> classBytes, ClassLoader parent) {
//        super(parent);
//        this.classBytes = classBytes;
//    }
//
//    @Override
//    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IN~~~~~~~~~~~~~~~~~~~~~~"+name);
//        if (this.classBytes != null) {
////            System.out.println("success:"+defineClass(name, this.classBytes, 0, this.classBytes.length));
//            return defineClass(name, this.classBytes, 0, this.classBytes.length);
//        }
//        System.out.println("fail:"+super.findClass(name));
//        return super.findClass(name);
//    }
}