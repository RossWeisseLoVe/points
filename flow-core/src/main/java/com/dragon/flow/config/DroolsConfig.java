//package com.dragon.flow.config;
//
//import org.kie.api.KieServices;
//import org.kie.api.builder.KieScanner;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DroolsConfig {
//
//    @Bean
//    public KieContainer kieContainer() {
//        KieServices kieServices = KieServices.Factory.get();
//        KieContainer kieContainer = kieServices.getKieClasspathContainer("SimpleRuleKBase");
////        KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
//        System.out.println("KieBases: " + kieContainer.getKieBaseNames());
////        kieScanner.scanNow();
//        return kieContainer;
//    }
//
//    @Bean
//    public KieSession kieSession() {
//        return kieContainer().newKieSession();
//    }
//}