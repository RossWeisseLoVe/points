package com.dragon.flow.config;

public class BaseContext {

    private BaseContext(){

    }

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setCurrentId(String id){
        THREAD_LOCAL.set(id);
    }

    public static String getCurrentId(){
        return THREAD_LOCAL.get();
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
