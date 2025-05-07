package com.dragon.flow.service.calculate.impl;

import com.dragon.flow.config.modeler.KieUtilClass;
import com.dragon.flow.model.aggregators.AggregatorsType1;
import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.flow.model.calculate.RegionInstanceModel;
import com.dragon.flow.model.calculate.RegionModel;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.service.calculate.*;
import com.dragon.flow.vo.calculate.BatchNewGhostVo;
import com.dragon.flow.vo.calculate.CalculateParamVo;
import com.dragon.flow.vo.calculate.GhostItem;
import com.dragon.flow.vo.calculate.GhostVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.beanutils.BeanUtils;
import org.bson.Document;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class CalculateServiceImpl implements CalculateService {

    @Autowired
    private KieUtilClass kieUtilClass;

    @Autowired
    private ModelsRepository modelsRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private RegionInstanceRepository regionInstanceRepository;

    @Autowired
    private RegionInstanceRepositoryImpl regionInstanceRepositoryImpl;

    @Override
    public CalculateModel getModelById(String id) {
        CalculateModel calculateModel = modelsRepository.findById(id).get();
        List<RegionModel> info = regionRepository.findByModelId(id);
        calculateModel.setTemplate(info);
        return calculateModel;
    }

    @Override
    @Transactional
    public InstanceModel newInstance(InstanceModel instanceModel,Boolean isOtherModel,Document relationOutForInstance,Document relationInForInstance,String fatherInstanceId) throws Exception{
        String modelId = instanceModel.getModelId();
        InstanceModel save = instanceRepository.save(instanceModel);
        String id = save.getId();
        CalculateModel calculateModel = this.getModelById(modelId);
        List<RegionModel> template = calculateModel.getTemplate();
        Map<String, Class<?>> classMap = kieUtilClass.getClassMap();
        List<RegionInstanceModel> regionInstanceModels = new ArrayList<>();
        template.forEach(item->{
            if(Objects.equals(item.getIsGhost(), 1)){
                return;
            }
            RegionInstanceModel regionInstanceModel = new RegionInstanceModel();
            regionInstanceModel.setInstanceId(id);
            regionInstanceModel.setRegionId(item.getId());
            regionInstanceModel.setClassName(item.getInfo().getClassName());
            regionInstanceModel.setRelationIn(item.getRelationIn());
            regionInstanceModel.setGhostRelationIn(item.getRelationIn());  //用于幽灵Region
            regionInstanceModel.setRelationOut(item.getRelationOut());
            regionInstanceModel.setProperties(item.getInfo().getProperties());
            regionInstanceModel.setDescription(item.getInfo().getDescription());
            regionInstanceModel.setType(item.getInfo().getType());
            if(isOtherModel==true&&relationOutForInstance!=null){
                Document relation = new Document();
                for (Map.Entry<String, Object> entry : relationOutForInstance.entrySet()) {
                    String key = entry.getKey();
                    List value = (List) entry.getValue();
                    List<Document> documents = new ArrayList<>();
                    value.forEach(rela->{
                        if(rela instanceof LinkedHashMap){
                            Document document = new Document((Map<String, Object>) rela);
                            document.put("fatherInstanceId",fatherInstanceId);
                            documents.add(document);
                        }else{
                            Document document = (Document) rela;
                            document.put("fatherInstanceId",fatherInstanceId);
                            documents.add(document);
                        }
                    });
                    String[] s = key.split("_");
                    if(s[1].equals(item.getId())){
                        relation.put(key,documents);
                    }
                }
                if(relation.isEmpty()){
                    regionInstanceModel.setRelationOut(item.getRelationOut());
                }else{
                    regionInstanceModel.setRelationOut(relation);
                }
            }
            if(isOtherModel==true&&relationInForInstance!=null){
                Document relation = new Document();
                for (Map.Entry<String, Object> entry : relationInForInstance.entrySet()) {
                    String key = entry.getKey();
                    List<Document> value = null;
                    if(entry.getValue() instanceof LinkedHashMap){
                        Document document = new Document((Map<String, Object>) entry.getValue());
                        value = (List<Document>) document;

                    }else{
                        value = (List<Document>) entry.getValue();
                    }
                    String[] s = key.split("_");
                    if(s[1].equals(item.getId())){
                        relation.put(key,value);
                    }
                }
                if(relation.isEmpty()){
                    regionInstanceModel.setRelationIn(item.getRelationIn());
                }else{
                    regionInstanceModel.setRelationIn(relation);
                }
            }
            String type = item.getInfo().getType();
            if(type.equals("othermodel")){
                //如果当前region是嵌套计算域，则递归生成实例
                String uuid = UUID.randomUUID().toString();
                InstanceModel nestingInstanceModel = new InstanceModel();
                nestingInstanceModel.setModelId(item.getInfo().getSourceModelId());
                nestingInstanceModel.setName(item.getInfo().getClassName());
                nestingInstanceModel.setDescription(instanceModel.getName()+"的子计算域");
                nestingInstanceModel.setFid(id);
                nestingInstanceModel.setLevel(instanceModel.getLevel()+1);
                nestingInstanceModel.setPath(instanceModel.getPath() == null ?
                        id : instanceModel.getPath() + "," + id);
                //以下两句代码的意义是：将regionInstance表中的嵌套模型实例与其每个计算域实例关联起来
                nestingInstanceModel.setId(uuid);
                nestingInstanceModel.setType("othermodel");
                regionInstanceModel.setId(uuid);
                try {
                    newInstance(nestingInstanceModel,true,item.getRelationOut(),item.getRelationIn(),id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(type.equals("Aggregators")){
                //如果该计算域为聚合器的话
                String className = item.getInfo().getClassName();
                if(className.equals("com.dragon.flow.model.aggregators.Average")){
                    AggregatorsType1 aggregatorsType1 = new AggregatorsType1();
                    Document stringDocumentMap = new Document();
                    if(regionInstanceModel.getRelationIn()!=null){
                        for (Map.Entry<String, Object> entry : regionInstanceModel.getRelationIn().entrySet()) {
                            String key = entry.getKey();
                            List<Document> value = (List<Document>) entry.getValue();
                            String[] s = key.split("_");
                            if(s[0].equals("list")){
                                value.forEach(relation->{
                                    String sourceObjId = (String) relation.get("sourceObjId");
                                    stringDocumentMap.put(sourceObjId,null);
                                });
                            }
                        }
                    }
                    aggregatorsType1.setDataMap(stringDocumentMap);
                    aggregatorsType1.setDataType("Double");
                    aggregatorsType1.setResult(null);
                    Document document = aggregatorsType1.toDocument();
                    regionInstanceModel.setData(document);
                }
                //根据不同的className执行不同的方法
            }else{
                try {
                    Class<?> aClass = classMap.get(item.getInfo().getClassName());
                    Object data = aClass.newInstance();
                    Document document = new Document();
                    for (Field field : aClass.getDeclaredFields()) {
                        field.setAccessible(true); // 强制访问私有字段
                        Object value = field.get(data);
                        document.put(field.getName(), value);
                    }
                    regionInstanceModel.setData(document);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            regionInstanceModels.add(regionInstanceModel);
        });
        List<RegionInstanceModel> regionInstanceModelSave = regionInstanceRepository.saveAll(regionInstanceModels);
        save.setData(regionInstanceModelSave);
        return save;
    }

    private void getAggregatorResult(CalculateParamVo paramVo, RegionInstanceModel regionInstanceModel){
        Document dataFromMongo = regionInstanceModel.getData();
        String typeName = regionInstanceModel.getClassName();
        if(typeName.equals("com.dragon.flow.model.aggregators.Average")){
            AggregatorsType1 aggregatorsType1 = AggregatorsType1.fromDocument(dataFromMongo);
            //获取到数据集合，查询是否全部都有值
            Document dataMap = aggregatorsType1.getDataMap();
            Document param = (Document) paramVo.getParam();
            System.out.println("========================================"+param.get("list"));
            dataMap.put(paramVo.getSourceId(), param.get("list"));
            AtomicBoolean flag = new AtomicBoolean(true);
            dataMap.forEach((key,value)->{
                if(value==null){
                    flag.set(false);
                }
            });
            if(flag.get()==true){
                aggregatorsType1.setResultAverage();
            }
            Document document = aggregatorsType1.toDocument();
            regionInstanceModel.setData(document);
            regionInstanceRepository.save(regionInstanceModel);
            Document relationOut = regionInstanceModel.getRelationOut();
            if(flag.get()==true&&relationOut!=null){
                this.sendNewTask(relationOut,regionInstanceModel,paramVo);
            }
        }
    }


    @Override
    public ReturnVo<Object> getCalculateInstance(CalculateParamVo param , boolean isFromWeb) throws Exception {
        ReturnVo<Object> returnVo = new ReturnVo<>();
        Object data = param.getParam();
        String fullName = param.getTypeName();
        //使用regionId和modelId套用模板
        String regionId = param.getRegionId();
        String instanceId = param.getInstanceId();
        //得到了模型中该计算域的信息，包括relationIn和Out
        //需要提供信息给对方的邻居节点
        //动态类map
        Map<String, Class<?>> clazzMap = kieUtilClass.getClassMap();
        KieContainer kieContainer = kieUtilClass.getKieContainer();
        KieSession kieSession = kieContainer.getKieBase().newKieSession();
        //从Object接收的param中取出付给新建的实例
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectNode objectNode = objectMapper.valueToTree(data);
        //用于记录是否达到计算的条件，默认不能计算
        AtomicBoolean flag = new AtomicBoolean(false);
        String regionInstanceId = param.getRegionInstanceId();
        RegionInstanceModel regionInstanceModelFromMongo;
        //创建实例
        Object instance;
        Class<?> regionClass;
        if(isFromWeb){
            //从客户端传来的请求
            //创建实例
            instance = clazzMap.get(fullName).newInstance();
            regionClass = clazzMap.get(fullName);
            regionInstanceModelFromMongo = regionInstanceRepository.findById(regionInstanceId).get();
            Document dataFromMongo = regionInstanceModelFromMongo.getData();
            objectNode.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                try {
                    Class<?> aClass = regionClass.getDeclaredField(fieldName).getType();
                    Object cast = aClass.cast(dataFromMongo.get(fieldName));
                    if(cast==null&&fieldValue!=null){
                        flag.set(true);
                    }else if (cast!=null&&!cast.equals(objectMapper.treeToValue((TreeNode) fieldValue,aClass))){
                        //如果数据发生改变即可计算
                        flag.set(true);
                    }
//                    System.out.println(fieldName + ": " + fieldValue + ":::" +cast);
                    this.callSetterMethod(instance,fieldName,fieldValue);
                } catch (NoSuchFieldException | JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }else{
            //程序自己异步调用
//            RegionInstanceModel regionInstanceModel = new RegionInstanceModel();
//            regionInstanceModel.setInstanceId(instanceId);
//            regionInstanceModel.setRegionId(regionId);
            regionInstanceModelFromMongo = regionInstanceRepository.findById(param.getRegionInstanceId()).get();
            Document dataFromMongo = regionInstanceModelFromMongo.getData();
            String type = regionInstanceModelFromMongo.getType();
            System.out.println("type==================="+type);
            //如果当前计算域为聚合器，则进入聚合器的计算流程
            if(type.equals("Aggregators")){
                this.getAggregatorResult(param,regionInstanceModelFromMongo);
                return null;
            }
            //由于类型map中不存在各聚合器的类型，为了防止报错，实例的创建放在这里
            instance = clazzMap.get(fullName).newInstance();
            regionClass = clazzMap.get(fullName);
            for (Map.Entry<String, Object> entry : dataFromMongo.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                if(fieldValue==null){
                    continue;
                }
                Class<?> aClass = entry.getValue().getClass();
                Object cast = aClass.cast(dataFromMongo.get(fieldName));
                if(fieldValue==null&&cast!=null){
                    //传入的值如果非空就赋给该属性
                    this.callSetterMethod(instance,fieldName,cast);
//                    System.out.println("写入值为cast："+cast);
                }else{
                    //其他情况下，该实例的本属性值保持不变
                    this.callSetterMethod(instance,fieldName,fieldValue);
//                    System.out.println("写入值为fieldValue："+fieldValue);
                }
//                System.out.println(fieldName + ": " + fieldValue + ":::" +cast);
            }
            Document autoData = (Document) param.getParam();
            //将传入的值赋给instance
            for (Map.Entry<String, Object> entry : autoData.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                Class<?> aClass = entry.getValue().getClass();
                Object cast = aClass.cast(dataFromMongo.get(fieldName));
                if(fieldValue!=null){
                    //传入的值如果非空就赋给该属性
                    this.callSetterMethod(instance,fieldName,fieldValue);
                    flag.set(true);
//                    System.out.println("传入的值");
                }
//                System.out.println(fieldName + ": " + fieldValue + ":::" +cast);
            }
        }
        if(flag.get()==false){
            returnVo.setMsg("暂时不具备计算条件或者数据未发生变化，数据已经保存");
            returnVo.setCode(ReturnCode.WARN);
            return returnVo;
        }
        //插入带值的实例
        kieSession.insert(instance);
        kieSession.fireAllRules();
        kieSession.dispose();
        //将结果存入数据库
        try {
            Document document = new Document();
            for (Field field : regionClass.getDeclaredFields()) {
                field.setAccessible(true); // 强制访问私有字段
                Object value = field.get(instance);
//                System.out.println("value-------------"+value+"-------"+value.getClass());
                document.put(field.getName(), value);
            }
            regionInstanceModelFromMongo.setData(document);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //还需要使用乐观锁处理线程安全问题
        regionInstanceRepository.save(regionInstanceModelFromMongo);
        Document relationOut = regionInstanceModelFromMongo.getRelationOut();
        if(relationOut!=null){
            this.sendNewTask(relationOut,regionInstanceModelFromMongo,param);
        }
        returnVo.setMsg("计算成功");
        returnVo.setData(instance);
        returnVo.setCode(ReturnCode.SUCCESS);
        return returnVo;
    }

    private void sendNewTask(Document relationOut,RegionInstanceModel regionInstanceModelFromMongo,CalculateParamVo paramVo){
        final Lock lock = new ReentrantLock();
        String regionId = paramVo.getRegionId();
        String instanceId = paramVo.getInstanceId();
        for (Map.Entry<String, Object> entry : relationOut.entrySet()) {
            String key = entry.getKey();
            List<Document> value = (List<Document>) entry.getValue();
            value.forEach(item->{
                String regionType = regionInstanceModelFromMongo.getType();
                Object dataValue = null;
                //构造条件
                String targetRegionType = item.get("targetRegionType", String.class);
                String targetObjId = item.get("targetObjId",String.class);
                String targetPropertyName = item.get("targetPropertyName",String.class);
                String fatherInstanceId = item.get("fatherInstanceId", String.class);
                String targetFatherRegionId = item.get("targetFatherRegionId",String.class);
                RegionInstanceModel regionInstanceModel = new RegionInstanceModel();
                //传递给下次计算的参数
                CalculateParamVo calculateParamVo = new CalculateParamVo();
                //四种情况 O2U U2O O2O U2U
                //如果fatherInstanceId不为空且targetRegionType为othermodel,则为O2O
                if(targetRegionType.equals("othermodel")&&fatherInstanceId!=null){
                    //O2O
                    RegionInstanceModel queryModel = new RegionInstanceModel();
                    queryModel.setRegionId(targetFatherRegionId);
                    queryModel.setInstanceId(fatherInstanceId);
                    Example<RegionInstanceModel> fatherExample = Example.of(queryModel);
                    RegionInstanceModel fatherRegionInstance = regionInstanceRepository.findOne(fatherExample).get();
                    String fid = fatherRegionInstance.getId();
                    regionInstanceModel.setInstanceId(fid);
                    regionInstanceModel.setRegionId(targetObjId);
                    String[] s = key.split("_");
                    dataValue = regionInstanceModelFromMongo.getData().get(s[0]);
                }else if(targetRegionType.equals("othermodel")&&fatherInstanceId==null){
                    //U2O
                    RegionInstanceModel queryModel = new RegionInstanceModel();
                    queryModel.setRegionId(targetFatherRegionId);
                    queryModel.setInstanceId(instanceId);
                    Example<RegionInstanceModel> fatherExample = Example.of(queryModel);
                    RegionInstanceModel fatherRegionInstance = regionInstanceRepository.findOne(fatherExample).get();
                    String fid = fatherRegionInstance.getId();
                    regionInstanceModel.setInstanceId(fid);
                    regionInstanceModel.setRegionId(targetObjId);
                    dataValue = regionInstanceModelFromMongo.getData().get(key);
                }else if((!targetRegionType.equals("othermodel"))&&fatherInstanceId!=null){
                    //O2U
                    regionInstanceModel.setInstanceId(fatherInstanceId);
                    regionInstanceModel.setRegionId(targetObjId);
                    String[] s = key.split("_");
                    dataValue = regionInstanceModelFromMongo.getData().get(s[0]);
                }else{
                    //U2U
                    regionInstanceModel.setInstanceId(instanceId);
                    regionInstanceModel.setRegionId(targetObjId);
                    if(regionType.equals("Aggregators")){
                        AggregatorsType1 aggregatorsType1 = AggregatorsType1.fromDocument(regionInstanceModelFromMongo.getData());
                        dataValue = aggregatorsType1.getResult();
                    }else{
                        dataValue = regionInstanceModelFromMongo.getData().get(key);
                    }
                }
                if(dataValue==null){
                    return;
                }
                //该部分作用为找到真正的对象regionInstance
                Example<RegionInstanceModel> regionInstanceModelExampleexample = Example.of(regionInstanceModel);
                //由于加入了幽灵Region的设计，所以以后不能再用
                RegionInstanceModel realTarget = regionInstanceRepository.findOne(regionInstanceModelExampleexample).get();
                String className = realTarget.getClassName();
                calculateParamVo.setTypeName(className);
                calculateParamVo.setInstanceId(realTarget.getInstanceId());
                calculateParamVo.setRegionId(realTarget.getRegionId());
                calculateParamVo.setSourceId(regionId);
                calculateParamVo.setRegionInstanceId(realTarget.getId());
                Document document = new Document();
                document.put(targetPropertyName,dataValue);
                calculateParamVo.setParam(document);
//                if(targetRegionType.equals("othermodel")){
//                    //如果目标是一个嵌套对象
//                    String[] s = key.split("_");
//                    dataValue = regionInstanceModelFromMongo.getData().get(s[0]);
//                    targetRegionId = s[1];
//                }else if(fatherInstanceId!=null) {
//                    String[] s = key.split("_");
//                    dataValue = regionInstanceModelFromMongo.getData().get(s[0]);
//                    targetRegionId = s[1];
//                    regionInstanceModel.setInstanceId(fatherInstanceId);
//                }else{
//                    if(regionType.equals("Aggregators")){
//                        Document dataFromMongo = regionInstanceModelFromMongo.getData();
//                        AggregatorsType1 aggregatorsType1 = AggregatorsType1.fromDocument(dataFromMongo);
//                        dataValue = aggregatorsType1.getResult();
//                    }else{
//                        dataValue = regionInstanceModelFromMongo.getData().get(key);
//                    }
//                }

                //异步调用
                CompletableFuture.runAsync(()->{
                    //此处会有线程安全问题，需要解决
                    lock.lock();
                    try {
                        this.getCalculateInstance(calculateParamVo,false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                });
            });
        }
    }


    @Override
    public  void callSetterMethod(Object obj, String propertyName,Object fieldValue) {
        // 获取对象的 Class 对象
        try {
            BeanUtils.setProperty(obj, propertyName, fieldValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setGhostInstance(GhostVo param) {
        List<BatchNewGhostVo> items = param.getItems();
        String instanceId = param.getInstanceId();
        String regionType = param.getRegionType();
        String regionInstanceId = param.getId();
        Map<String, Class<?>> classMap = kieUtilClass.getClassMap();
        RegionInstanceModel targetAgg = regionInstanceRepository.findById(regionInstanceId).get();
        InstanceModel instanceModel = instanceRepository.findById(instanceId).get();
        List<RegionInstanceModel> regionInstanceModelList = new ArrayList<>();
        //这个是为了找到所有source的准备
        List<Example<RegionInstanceModel>> sourceRegionInstanceModelExamples = new ArrayList<>();
        Map<String, Map<String, List<Document>>> relationInMap = new HashMap<>();
        Set<String> ghostRegionIds = new HashSet<>();
        ArrayList<Document> relationOutList = new ArrayList<>();
        items.forEach(item->{
            List<GhostItem> sourceRegions = item.getSourceRegions();
            sourceRegions.forEach(ghost->{
                RegionModel region = ghost.getRegion();
                Integer count = ghost.getCount();
                //构建一下给目标的relationIn
                Document relationIn = region.getRelationIn();
                Document relationOut = region.getRelationOut();
                String id = region.getId();
                ghostRegionIds.add(id);
                String type = region.getInfo().getType();
                List<Document> exampleRelations = new ArrayList<>();
                if(relationIn!=null&&!relationIn.isEmpty()){
                    for (Map.Entry<String, Object> entry : relationIn.entrySet()) {
                        String key = entry.getKey();
                        ArrayList<LinkedHashMap<String, String>> value = (ArrayList<LinkedHashMap<String,String>>) entry.getValue();
                        value.forEach(rela->{
                            Document relation = new Document();
                            relation.put("targetPropertyName",key);
                            relation.put("targetRegionType",type);
                            relation.put("sourcePropertyName",rela.get("sourcePropertyName"));
                            relation.put("sourceObjId",rela.get("sourceObjId"));
                            relation.put("sourceRegionType",rela.get("sourceRegionType"));
                            exampleRelations.add(relation);
                            RegionInstanceModel example = new RegionInstanceModel();
                            example.setInstanceId(instanceId);
                            example.setRegionId(rela.get("sourceObjId"));
                            Example<RegionInstanceModel> regionInstanceModelExampleexample = Example.of(example);
                            sourceRegionInstanceModelExamples.add(regionInstanceModelExampleexample);
                        });
                    }
                }
                if(relationOut!=null&&!relationOut.isEmpty()){
                    for (Map.Entry<String, Object> entry : relationOut.entrySet()) {
                        String key = entry.getKey();
                        ArrayList<LinkedHashMap<String, String>> value = (ArrayList<LinkedHashMap<String,String>>) entry.getValue();
                        value.forEach(rela->{
                            Document relation = new Document();
                            relation.put("sourcePropertyName",key);
                            relation.put("sourceRegionType",type);
                            relationOutList.add(relation);
                        });
                    }
                }
                for (int i = 0; i < count; i++) {
                    RegionInstanceModel regionInstanceModel = new RegionInstanceModel();
                    regionInstanceModel.setInstanceId(instanceId);
                    String uuid = UUID.randomUUID().toString();
                    regionInstanceModel.setRegionId(uuid);
                    regionInstanceModel.setClassName(region.getInfo().getClassName());
                    regionInstanceModel.setRelationIn(region.getRelationIn());
                    regionInstanceModel.setRelationOut(region.getRelationOut());
                    regionInstanceModel.setProperties(region.getInfo().getProperties());
                    regionInstanceModel.setDescription(region.getInfo().getDescription());
                    regionInstanceModel.setType(region.getInfo().getType());
                    relationOutList.forEach(rela->{
                        rela.put("sourceObjId",uuid);
                        AggregatorsType1 aggregatorsType1 = AggregatorsType1.fromDocument(targetAgg.getData());
                        aggregatorsType1.getDataMap().put(uuid,null);
                        Document document = aggregatorsType1.toDocument();
                        targetAgg.setData(document);
                    });
                    exampleRelations.forEach(rela->{
                        String sourceObjId = rela.get("sourceObjId",String.class);
                        if(relationInMap.get(sourceObjId)==null){
                            Map<String, List<Document>> stringDocumentMap = new HashMap<>();
                            relationInMap.put(sourceObjId,stringDocumentMap);
                        }
                        Map<String, List<Document>> stringDocumentMap = relationInMap.get(sourceObjId);
                        Document singleRelationOut = new Document();
                        String sourcePropertyName = rela.getString("sourcePropertyName");

                        singleRelationOut.put("targetPropertyName",rela.getString("targetPropertyName"));
                        singleRelationOut.put("targetRegionType",rela.getString("targetRegionType"));
                        singleRelationOut.put("targetObjId",uuid);

                        if(stringDocumentMap.get(sourcePropertyName)==null){
                            stringDocumentMap.put(sourcePropertyName,new ArrayList<>());
                        }
                        List<Document> documents = stringDocumentMap.get(sourcePropertyName);
                        documents.add(singleRelationOut);
                    });
                    if(type.equals("othermodel")){
                        //需要先生成实例到Instance中
                        //如果当前region是嵌套计算域，则递归生成实例
                        String uuid2 = UUID.randomUUID().toString();
                        InstanceModel nestingInstanceModel = new InstanceModel();
                        nestingInstanceModel.setModelId(region.getInfo().getSourceModelId());
                        nestingInstanceModel.setName(region.getInfo().getClassName());
                        nestingInstanceModel.setDescription(instanceModel.getName()+"的子计算域");
                        nestingInstanceModel.setFid(instanceId);
                        nestingInstanceModel.setLevel(instanceModel.getLevel()+1);
                        nestingInstanceModel.setPath(instanceModel.getPath() == null ?
                                instanceId : instanceModel.getPath() + "," + instanceId);
                        //以下两句代码的意义是：将regionInstance表中的嵌套模型实例与其每个计算域实例关联起来
                        nestingInstanceModel.setId(uuid2);
                        nestingInstanceModel.setType("othermodel");
                        regionInstanceModel.setId(uuid2);
                        try {
                            newInstance(nestingInstanceModel,true,region.getRelationOut(),region.getRelationIn(),instanceId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(type.equals("Aggregators")){
                        //如果该计算域为聚合器的话
                        String className = region.getInfo().getClassName();
                        if(className.equals("com.dragon.flow.model.aggregators.Average")){
                            AggregatorsType1 aggregatorsType1 = new AggregatorsType1();
                            Document stringDocumentMap = new Document();
                            if(regionInstanceModel.getRelationIn()!=null){
                                for (Map.Entry<String, Object> entry : regionInstanceModel.getRelationIn().entrySet()) {
                                    String key = entry.getKey();
                                    List<Document> value = (List<Document>) entry.getValue();
                                    String[] s = key.split("_");
                                    if(s[0].equals("list")){
                                        value.forEach(relation->{
                                            String sourceObjId = (String) relation.get("sourceObjId");
                                            stringDocumentMap.put(sourceObjId,null);
                                        });
                                    }
                                }
                            }
                            aggregatorsType1.setDataMap(stringDocumentMap);
                            aggregatorsType1.setDataType("Double");
                            aggregatorsType1.setResult(null);
                            Document document = aggregatorsType1.toDocument();
                            regionInstanceModel.setData(document);
                        }
                        //根据不同的className执行不同的方法
                    }else{
                        try {
                            Class<?> aClass = classMap.get(region.getInfo().getClassName());
                            Object data = aClass.newInstance();
                            Document document = new Document();
                            for (Field field : aClass.getDeclaredFields()) {
                                field.setAccessible(true); // 强制访问私有字段
                                Object value = field.get(data);
                                document.put(field.getName(), value);
                            }
                            regionInstanceModel.setData(document);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    regionInstanceModelList.add(regionInstanceModel);
                }
            });
        });
        AggregatorsType1 agg = AggregatorsType1.fromDocument(targetAgg.getData());
        Document dataMap = agg.getDataMap();
        ghostRegionIds.forEach(item->{
            dataMap.remove(item);
        });
        Document document = agg.toDocument();
        targetAgg.setData(document);
        List<RegionInstanceModel> regionInstanceModelsByCriteria = regionInstanceRepositoryImpl.findRegionInstanceModelsByCriteria(sourceRegionInstanceModelExamples);
        regionInstanceModelsByCriteria.forEach(regionInstance->{
            String regionId = regionInstance.getRegionId();
            Document relationOut = regionInstance.getRelationOut();
            if(relationOut!=null){
                Map<String, List<Document>> stringListMap = relationInMap.get(regionId);
                for (Map.Entry<String, Object> entry : relationOut.entrySet()) {
                    String key = entry.getKey();
                    ArrayList<Document> value = (ArrayList<Document>) entry.getValue();
                    //需要把幽灵的删去
                    //需要看看是否该项已经有值，有值的话要直接写进去
                    List<Document> realRelations = new ArrayList<>();
                    value.forEach(item->{
                        if(!ghostRegionIds.contains(item.getString("targetObjId"))){
                            realRelations.add(item);
                        }
                    });
                    List<Document> documents = stringListMap.get(key);
                    realRelations.addAll(documents);
                    relationOut.put(key,realRelations);
                }
            }
        });
        Document relationIn = targetAgg.getRelationIn();
        if(relationIn!=null){
            for (Map.Entry<String, Object> entry : relationIn.entrySet()) {
                String key = entry.getKey();
                ArrayList<Document> value = (ArrayList<Document>) entry.getValue();
                if(key.equals("list")){
                    List<Document> realRelations = new ArrayList<>();
                    value.forEach(item->{
                        if(!ghostRegionIds.contains(item.getString("sourceObjId"))){
                            realRelations.add(item);
                        }
                    });
                    realRelations.addAll(relationOutList);
                    relationIn.put(key,realRelations);
                }
            }
        }
        regionInstanceModelList.addAll(regionInstanceModelsByCriteria);
        regionInstanceModelList.add(targetAgg);
        regionInstanceRepository.saveAll(regionInstanceModelList);
        regionInstanceModelsByCriteria.forEach(item->{
            if(item.getRelationOut()!=null){
                CalculateParamVo calculateParamVo = new CalculateParamVo();
                calculateParamVo.setInstanceId(item.getInstanceId());
                calculateParamVo.setRegionId(item.getRegionId());
                this.sendNewTask(item.getRelationOut(),item,calculateParamVo);
            }
        });
    }
}
