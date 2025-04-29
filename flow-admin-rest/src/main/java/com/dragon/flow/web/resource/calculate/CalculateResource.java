package com.dragon.flow.web.resource.calculate;


import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.flow.model.calculate.RegionInstanceModel;
import com.dragon.flow.model.calculate.RegionModel;
import com.dragon.flow.model.customer.Activity;
import com.dragon.flow.service.calculate.*;
import com.dragon.flow.service.calculate.impl.InstanceImpl;
import com.dragon.flow.vo.calculate.BatchNewGhostVo;
import com.dragon.flow.vo.calculate.CalculateParamVo;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flow/calculate")
public class CalculateResource {

    @Autowired
    private CalculateService calculateService;

    @Autowired
    private ModelsRepository modelsRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private RegionInstanceRepository regionInstanceRepository;

    @Autowired
    private InstanceImpl instanceImpl;

//    @PostMapping("/reload")
//    public ReturnVo<String> reload() throws Exception {
//        droolsConfig.refreshBean();
//        ReturnVo<String> stringReturnVo = new ReturnVo<>();
//        stringReturnVo.setCode(ReturnCode.SUCCESS);
//        stringReturnVo.setMsg("重载成功");
//        return stringReturnVo;
//    }

//    @PostMapping("getResult")
//    public ReturnVo<Object> getCalculateInstance(@RequestBody Object param,@RequestParam String typeName) throws Exception {
//        Object calculateInstance = calculateService.getCalculateInstance(param, typeName);
//        ReturnVo<Object> calculateReturnVo = new ReturnVo<>();
//        calculateReturnVo.setCode(ReturnCode.SUCCESS);
//        calculateReturnVo.setData(calculateInstance);
//        calculateReturnVo.setMsg("计算成功");
//        return calculateReturnVo;
//    }

    @Transactional
    @PostMapping("saveModel")
    public ReturnVo<CalculateModel> saveModel(@RequestBody CalculateModel calculateModel){
        //根据id是否为空判断是插入还是更新
        if(calculateModel.getId()==null){
            calculateModel.setCreateTime(new Date());
        }else{
            calculateModel.setUpdateTime(new Date());
        }
        ReturnVo<CalculateModel> returnVo = new ReturnVo<>();
        List<RegionModel> regionModelList = calculateModel.getTemplate();
        CalculateModel save = modelsRepository.save(calculateModel);
        String id = save.getId();
        regionModelList.forEach(item->{
            item.setModelId(id);
        });
        //若为更新需要先删除旧的
        if(calculateModel.getId()!=null){
            regionRepository.deleteAllByModelId(calculateModel.getId());
        }
        regionRepository.saveAll(regionModelList);
        returnVo.setData(calculateModel);
        returnVo.setCode(ReturnCode.SUCCESS);
        returnVo.setMsg("保存成功");
        return returnVo;
    }

    @Transactional
    @PostMapping("deleteModel")
    public ReturnVo deleteModel(@RequestBody CalculateModel calculateModel){
        String id = calculateModel.getId();
        modelsRepository.deleteById(id);
        regionRepository.deleteAllByModelId(id);
        ReturnVo returnVo = new ReturnVo();
        returnVo.setCode(ReturnCode.SUCCESS);
        returnVo.setMsg("删除成功");
        return returnVo;
    }


    @PostMapping("getModels")
    public ReturnVo<List<CalculateModel>> getModels(@RequestBody ParamVo<CalculateModel> param){
        int pageNum = param.getQuery().getPageNum();
        int pageSize = param.getQuery().getPageSize();
        CalculateModel calculateModel = new CalculateModel();
        calculateModel.setName(param.getEntity().getName());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 模糊匹配
                .withIgnoreCase(true);
        Example<CalculateModel> calculateModelExample = Example.of(calculateModel,matcher);
        Pageable pageable = PageRequest.of(pageNum - 1,pageSize);
        Page<CalculateModel> all = modelsRepository.findAll(calculateModelExample,pageable);
        List<CalculateModel> content = all.getContent();
        ReturnVo<List<CalculateModel>> listReturnVo = new ReturnVo<>();
        listReturnVo.setCode(ReturnCode.SUCCESS);
        listReturnVo.setData(content);
        listReturnVo.setMsg("查询成功");
        return listReturnVo;
    }

    @GetMapping("getAllModels")
    public ReturnVo<List<CalculateModel>> getAllModels(){
        List<CalculateModel> all = modelsRepository.findAll();
        ReturnVo<List<CalculateModel>> listReturnVo = new ReturnVo<>();
        listReturnVo.setCode(ReturnCode.SUCCESS);
        listReturnVo.setData(all);
        listReturnVo.setMsg("查询成功");
        return listReturnVo;
    }

    @GetMapping("getModelById/{id}")
    public ReturnVo<CalculateModel> getModelById(@PathVariable String id){
        CalculateModel model = calculateService.getModelById(id);
        ReturnVo<CalculateModel> calculateModelReturnVo = new ReturnVo<>();
        calculateModelReturnVo.setMsg("查询成功");
        calculateModelReturnVo.setCode(ReturnCode.SUCCESS);
        calculateModelReturnVo.setData(model);
        return calculateModelReturnVo;
    }

    @PostMapping("newInstance")
    public ReturnVo<InstanceModel> newInstance(@RequestBody InstanceModel instanceModel) throws Exception {
        instanceModel.setType("independent");
        instanceModel.setLevel(0);
        instanceModel.setPath(null);
        instanceModel.setFid(null);
        InstanceModel one = calculateService.newInstance(instanceModel,false,null,null,null);
        ReturnVo<InstanceModel> regionModelReturnVo = new ReturnVo<>();
        regionModelReturnVo.setData(one);
        regionModelReturnVo.setMsg("实例化成功");
        regionModelReturnVo.setCode(ReturnCode.SUCCESS);
        return regionModelReturnVo;
    }

    @PostMapping("updateInstance")
    public ReturnVo<InstanceModel> updateInstance(@RequestBody InstanceModel instanceModel) throws Exception {
        InstanceModel save = instanceRepository.save(instanceModel);
        ReturnVo<InstanceModel> regionModelReturnVo = new ReturnVo<>();
        regionModelReturnVo.setData(save);
        regionModelReturnVo.setMsg("更新成功");
        regionModelReturnVo.setCode(ReturnCode.SUCCESS);
        return regionModelReturnVo;
    }

    @PostMapping("getInstancePageByModelId")
    public ReturnVo<List<InstanceModel>> getInstancePageByModelId(@RequestBody ParamVo<InstanceModel> param){
        int pageSize = param.getQuery().getPageSize();
        int pageNum = param.getQuery().getPageNum();
        InstanceModel entity = param.getEntity();
        Pageable pageable = PageRequest.of(pageNum - 1,pageSize);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 模糊匹配
                .withIgnoreCase(true);
        Example<InstanceModel> example = Example.of(entity,matcher);
        Page<InstanceModel> page = instanceRepository.findAll(example,pageable);
        List<InstanceModel> rootsWithSubtrees = instanceImpl.getRootsWithSubtrees(page);
        List<InstanceModel> content = page.getContent();
        ReturnVo<List<InstanceModel>> listReturnVo = new ReturnVo<>();
        listReturnVo.setCode(ReturnCode.SUCCESS);
        listReturnVo.setMsg("查询成功");
        listReturnVo.setData(rootsWithSubtrees);
        return listReturnVo;
    }

    @GetMapping("getRegionInstanceModelListById/{id}")
    public ReturnVo<List<RegionInstanceModel>> getRegionInstanceModelListById(@PathVariable String id){
        List<RegionInstanceModel> regionInstanceModelList = regionInstanceRepository.findAllByInstanceId(id);
        return new ReturnVo<List<RegionInstanceModel>>(ReturnCode.SUCCESS,"查询成功",regionInstanceModelList);
    }

    @PostMapping("getRegionInstanceById")
    public ReturnVo<RegionInstanceModel> getRegionInstanceById(@RequestBody CalculateParamVo param){
        String id = param.getRegionInstanceId();
        RegionInstanceModel regionInstanceModel = regionInstanceRepository.findById(id).get();
        ReturnVo<RegionInstanceModel> regionInstanceModelReturnVo = new ReturnVo<>(ReturnCode.SUCCESS,"查询成功",regionInstanceModel);
        return regionInstanceModelReturnVo;
    }

    @PostMapping("executeRegion")
    public ReturnVo<Object> executeRegion(@RequestBody CalculateParamVo param) throws Exception {
        ReturnVo<Object> calculateInstance = calculateService.getCalculateInstance(param, true);
        return calculateInstance;
    }


    @PostMapping("getRegionsByIds")
    public ReturnVo<List<RegionModel>> getRegionsByIds(@RequestBody List<String> items){
        List<RegionModel> allById = regionRepository.findAllById(items);
        ReturnVo<List<RegionModel>> listReturnVo = new ReturnVo<>();
        listReturnVo.setMsg("查询成功");
        listReturnVo.setCode(ReturnCode.SUCCESS);
        listReturnVo.setData(allById);
        return listReturnVo;
    }

    @PostMapping("setGhostInstance")
    public ReturnVo setGhostInstance(@RequestBody List<BatchNewGhostVo> items){
        calculateService.setGhostInstance(items);
        items.forEach(item->{
            System.out.println("=========="+item.toString());
        });
        return new ReturnVo(ReturnCode.SUCCESS,"新增成功",null);
    }


}
