package com.dragon.flow.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SetterWatchConfig {

    public void PrintMessage(Object newVal,Object oldVal){
        System.out.println("新值为：" + newVal);
        System.out.println("旧值为：" + oldVal);
    }

}
