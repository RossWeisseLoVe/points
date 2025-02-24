package com.dragon.flow.config;

import com.dragon.flow.model.generate.ClassDefinition;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DroolsConfig {

    @Bean
    public KieContainer kieContainer() throws Exception {
        //读取EXCEL文件
        String filePath = "E:/workspace/points/flow-master/flow-core/src/main/resources/rules/rule.xls";
        List<ClassDefinition> classList = parseExcel(filePath);

        return null;
    }

    private static List<ClassDefinition> parseExcel(String filePath) throws Exception {
        List<ClassDefinition> result = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)){
             Sheet sheet = workbook.getSheetAt(1);

        }
    }

    @Bean
    public KieSession kieSession() {
        return kieContainer().getKieBase().newKieSession();
    }
}