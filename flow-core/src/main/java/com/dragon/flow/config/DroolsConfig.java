package com.dragon.flow.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dragon.flow.config.modeler.KieUtilClass;
import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.service.generate.ClassDefinitionService;
import com.dragon.flow.service.generate.PropertyDefinitionService;
import com.dragon.flow.utils.JavaSourceGenerator;
import com.itranswarp.compiler.JavaStringCompiler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Configuration
public class DroolsConfig {

    private volatile KieUtilClass kieUtill;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ClassDefinitionService classDefinitionService;

    @Autowired
    private PropertyDefinitionService propertyDefinitionService;

    @Transactional
    public KieUtilClass kieUtilCreator() throws Exception {
        //读取EXCEL文件
//        String filePath = "E:/Java/workspace/points/points/flow-core/src/main/resources/rules/rule.xls";
        String filePath = "E:/workspace/points/flow-master/flow-core/src/main/resources/rules/rule.xls";
//        String filePath = "E:/workspace/points/flow-core/src/main/resources/rules/rule.xls";
        List<ClassDefinition> classList = parseExcel(filePath);
        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, Class<?>> clazzMap = new HashMap<>();
        Map<String, KieContainer> kieContainerMap = new HashMap<>();
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        SpreadsheetCompiler compilerExcel = new SpreadsheetCompiler();
        String drl = compilerExcel.compile(new FileInputStream(filePath), InputType.XLS);
        kieFileSystem.write("src/main/resources/rules.drl", drl);
        System.out.println(drl);
        QueryWrapper<ClassDefinition> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("id");
        classDefinitionService.remove(wrapper);
        QueryWrapper<PropertyDefinition> pwrapper = new QueryWrapper<>();
        wrapper.isNotNull("id");
        propertyDefinitionService.remove(pwrapper);
        Map<String, byte[]> bytecodeMap = new HashMap<>();
        classList.forEach(item->{
            String sourceCode = JavaSourceGenerator.generateSourceCode(item);
            System.out.println("sourceCode:"+ sourceCode);
            String fullName = item.getClassName();
            try {
                Map<String, byte[]> results = compiler.compile((fullName).replace('.', '/') + ".java", sourceCode);
                String classFilePath = "src/main/resources/" + fullName.replace('.', '/') + ".class";
                kieFileSystem.write(classFilePath,results.get(fullName));
                Class<?> clazz = compiler.loadClass(fullName, results);
                clazzMap.put(fullName,clazz);
                bytecodeMap.put(fullName, results.get(fullName));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            //存入数据库
            item.setSourceCode(sourceCode);
            classDefinitionService.save(item);
            String id = item.getId();
            item.getProperties().forEach(x->{
                x.setClassId(id);
            });
            propertyDefinitionService.saveBatch(item.getProperties());
        });

        ClassLoader sharedClassLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                byte[] bytecode = bytecodeMap.get(name);
                if (bytecode != null) {
                    return defineClass(name, bytecode, 0, bytecode.length);
                }
                throw new ClassNotFoundException(name);
            }
        };

        // 加载所有类到 clazzMap
        for (String fullName : bytecodeMap.keySet()) {
            try {
                Class<?> clazz = sharedClassLoader.loadClass(fullName);
                clazzMap.put(fullName, clazz);
                System.out.println("Loaded " + fullName + " with ClassLoader: " + clazz.getClassLoader());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
//        classList.forEach(item->{
//            String fullName = item.getClassName();
//            try {
//                Class<?> dynamicClass = sharedClassLoader.loadClass(fullName);
//                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(dynamicClass);
//                beanFactory.registerBeanDefinition("dynamicClass", builder.getBeanDefinition());
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        });

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        Results resultsBuilder = kieBuilder.getResults();
        System.out.println("Messages:"+resultsBuilder.getMessages());
        //输出错误信息
        if (resultsBuilder.hasMessages(Message.Level.ERROR)) {
            System.err.println(resultsBuilder.getMessages());
            throw new RuntimeException("Build Errors: " + resultsBuilder.getMessages());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId(),sharedClassLoader);
        KieUtilClass kieUtilClass = new KieUtilClass();
        kieUtilClass.setClassMap(clazzMap);
        kieUtilClass.setKieContainer(kieContainer);



        return kieUtilClass;
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
                    currentClass.setDescription(getCellValue(row.getCell(3)));
                } else if (currentClass != null&&!"propertyName".equalsIgnoreCase(getCellValue(typeCell))) {
                    // 处理属性行（假设属性行格式：第二列为属性名，第三列为类型）
                    String propertyName = getCellValue(row.getCell(1));
                    String propertyType = getCellValue(row.getCell(2));
                    String inputOrOutput = getCellValue(row.getCell(3));
                    String formItem = getCellValue(row.getCell(4));
                    String formItemName = getCellValue(row.getCell(5));
                    String displayBy = getCellValue(row.getCell(6));
                    Integer min = getCellValueInteger(row.getCell(7));
                    Integer max = getCellValueInteger(row.getCell(8));
                    Integer decimalPoint = getCellValueInteger(row.getCell(9));
                    String placeHolder = getCellValue(row.getCell(10));
                    String options = getCellValue(row.getCell(11));
                    PropertyDefinition propertyDefinition = new PropertyDefinition();
                    propertyDefinition.setPropertyName(propertyName);
                    propertyDefinition.setPropertyType(propertyType);
                    propertyDefinition.setInputOrOutput(inputOrOutput);
                    propertyDefinition.setFormItem(formItem);
                    propertyDefinition.setFormItemName(formItemName);
                    propertyDefinition.setDisplayBy(displayBy);
                    propertyDefinition.setMin(min);
                    propertyDefinition.setMax(max);
                    propertyDefinition.setDecimalPoint(decimalPoint);
                    propertyDefinition.setPlaceholder(placeHolder);
                    propertyDefinition.setOptions(options);
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

    private static Integer getCellValueInteger(Cell cell){
        if (cell == null) return null;
        DataFormatter formatter = new DataFormatter();
        if("".equalsIgnoreCase(formatter.formatCellValue(cell).trim())) return null;
        return Integer.parseInt(formatter.formatCellValue(cell).trim());
    }

    @Bean
    public KieUtilClass createBean() throws Exception {
        if (kieUtill == null) {
            kieUtill = kieUtilCreator();
        }
        return kieUtill;
    }

    public void refreshBean() throws Exception {
        kieUtill = kieUtilCreator();
    }

}