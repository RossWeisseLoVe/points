package com.dragon.flow.config;

import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.utils.JavaSourceGenerator;
import com.itranswarp.compiler.JavaStringCompiler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class DroolsConfig {

    @Bean
    public KieContainer kieContainer() throws Exception {
        //读取EXCEL文件
        String packageName = "com.dragon.flow.model.test";
        String filePath = "E:/Java/workspace/points/points/flow-core/src/main/resources/rules/rule.xls";
        List<ClassDefinition> classList = parseExcel(filePath);
        JavaStringCompiler compiler = new JavaStringCompiler();

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        SpreadsheetCompiler compilerExcel = new SpreadsheetCompiler();
        String drl = compilerExcel.compile(new FileInputStream(filePath), InputType.XLS);
        kieFileSystem.write("src/main/resources/rules.drl", drl);
        System.out.println(drl);
        final ClassLoader[] classLoader = {null};
        classList.forEach(item->{
            String sourceCode = JavaSourceGenerator.generateSourceCode(item);
            System.out.println("sourceCode:"+ sourceCode);
            String fullName = item.getClassName();
            try {
                Map<String, byte[]> results = compiler.compile((fullName).replace('.', '/') + ".java", sourceCode);
                String classFilePath = "src/main/resources/" + fullName.replace('.', '/') + ".class";
                kieFileSystem.write(classFilePath,results.get(fullName));
                Class<?> clazz = compiler.loadClass(fullName, results);
                if(classLoader[0] ==null){
                    classLoader[0] = clazz.getClassLoader();
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        Results resultsBuilder = kieBuilder.getResults();
        System.out.println("Messages:"+resultsBuilder.getMessages());
        //输出错误信息
        if (resultsBuilder.hasMessages(Message.Level.ERROR)) {
            System.err.println(resultsBuilder.getMessages());
            throw new RuntimeException("Build Errors: " + resultsBuilder.getMessages());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId(),classLoader[0]);
        return kieContainer;
    }

    private static List<ClassDefinition> parseExcel(String filePath) throws Exception {
        List<ClassDefinition> result = new ArrayList<>();
        String packageName = "com.dragon.flow.model.test";
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new HSSFWorkbook(fis)){
             Sheet sheet = workbook.getSheetAt(1);
            ClassDefinition currentClass = null;

            for (Row row : sheet) {
                // 跳过第一列（索引0），从第二列（索引1）开始解析
                Cell typeCell = row.getCell(1);
                Cell nameCell = row.getCell(2);
                if(typeCell == null||"".equalsIgnoreCase(getCellValue(typeCell))){
                    continue;
                }
                // 检查是否为 "className" 行
                if ("className".equalsIgnoreCase(getCellValue(typeCell))) {
                    // 保存上一个类的数据（如果有）
                    if (currentClass != null) {
                        result.add(currentClass);
                    }
                    // 创建新类对象
                    currentClass = new ClassDefinition();
                    currentClass.setClassName(packageName+"."+getCellValue(nameCell));
                    currentClass.setProperties(new ArrayList<>());
                } else if (currentClass != null&&!"propertyName".equalsIgnoreCase(getCellValue(typeCell))) {
                    // 处理属性行（假设属性行格式：第二列为属性名，第三列为类型）
                    String propertyName = getCellValue(row.getCell(1));
                    String propertyType = getCellValue(row.getCell(2));
                    String inputOrOutput = getCellValue(row.getCell(3));
                    String formItem = getCellValue(row.getCell(4));
                    String displayBy = getCellValue(row.getCell(5));
                    String min = getCellValue(row.getCell(6));
                    String max = getCellValue(row.getCell(7));
                    String decimalPoint = getCellValue(row.getCell(8));
                    PropertyDefinition propertyDefinition = new PropertyDefinition();
                    propertyDefinition.setPropertyName(propertyName);
                    propertyDefinition.setPropertyType(propertyType);
                    propertyDefinition.setInputOrOutput(inputOrOutput);
                    propertyDefinition.setFormItem(formItem);
                    propertyDefinition.setDisplayBy(displayBy);
                    propertyDefinition.setMin(min);
                    propertyDefinition.setMax(max);
                    propertyDefinition.setDecimalPoint(decimalPoint);
                    currentClass.getProperties().add(propertyDefinition);
                }
            }
            if (currentClass != null) {
                result.add(currentClass);
            }

        }
        return result;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    @Bean
    public KieSession kieSession() throws Exception {
        return kieContainer().getKieBase().newKieSession();
    }
}