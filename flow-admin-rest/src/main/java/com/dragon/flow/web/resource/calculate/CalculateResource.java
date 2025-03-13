package com.dragon.flow.web.resource.calculate;


import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.flow.model.calculate.RegionInstanceModel;
import com.dragon.flow.model.calculate.RegionModel;
import com.dragon.flow.model.customer.Activity;
import com.dragon.flow.service.calculate.*;
import com.dragon.flow.vo.calculate.CalculateParamVo;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
        regionRepository.saveAll(regionModelList);
        returnVo.setData(calculateModel);
        returnVo.setCode(ReturnCode.SUCCESS);
        returnVo.setMsg("保存成功");
        return returnVo;
    }

    @PostMapping("getModels")
    public ReturnVo<List<CalculateModel>> getModels(@RequestBody ParamVo<CalculateModel> param){
        int pageNum = param.getQuery().getPageNum();
        int pageSize = param.getQuery().getPageSize();
//        CalculateModel calculateModel = new CalculateModel();
//        Example<CalculateModel> calculateModelExample = Example.of(calculateModel);
        Pageable pageable = PageRequest.of(pageNum - 1,pageSize);
        Page<CalculateModel> all = modelsRepository.findAll(pageable);
        List<CalculateModel> content = all.getContent();
        ReturnVo<List<CalculateModel>> listReturnVo = new ReturnVo<>();
        listReturnVo.setCode(ReturnCode.SUCCESS);
        listReturnVo.setData(content);
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
        InstanceModel one = calculateService.newInstance(instanceModel);
        ReturnVo<InstanceModel> regionModelReturnVo = new ReturnVo<>();
        regionModelReturnVo.setData(one);
        regionModelReturnVo.setMsg("实例化成功");
        regionModelReturnVo.setCode(ReturnCode.SUCCESS);
        return regionModelReturnVo;
    }


    @PostMapping("getInstancePageByModelId")
    public ReturnVo<List<InstanceModel>> getInstancePageByModelId(@RequestBody ParamVo<InstanceModel> param){
        int pageSize = param.getQuery().getPageSize();
        int pageNum = param.getQuery().getPageNum();
        InstanceModel entity = param.getEntity();
        Pageable pageable = PageRequest.of(pageNum - 1,pageSize);
        Example<InstanceModel> example = Example.of(entity);
        Page<InstanceModel> page = instanceRepository.findAll(example,pageable);
        List<InstanceModel> content = page.getContent();
        ReturnVo<List<InstanceModel>> listReturnVo = new ReturnVo<>();
        listReturnVo.setCode(ReturnCode.SUCCESS);
        listReturnVo.setMsg("查询成功");
        listReturnVo.setData(content);
        return listReturnVo;
    }

    @GetMapping("getRegionInstanceModelListById/{id}")
    public ReturnVo<List<RegionInstanceModel>> getRegionInstanceModelListById(@PathVariable String id){
        List<RegionInstanceModel> regionInstanceModelList = regionInstanceRepository.findAllByInstanceId(id);
        return new ReturnVo<List<RegionInstanceModel>>(ReturnCode.SUCCESS,"查询成功",regionInstanceModelList);
    }

    @PostMapping("getRegionInstanceByRegionIdAndInstanceId")
    public ReturnVo<RegionInstanceModel> getRegionInstanceByRegionIdAndInstanceId(@RequestBody CalculateParamVo param){
        String regionId = param.getRegionId();
        String instanceId = param.getInstanceId();
        RegionInstanceModel regionInstanceModel = new RegionInstanceModel();
        regionInstanceModel.setInstanceId(instanceId);
        regionInstanceModel.setRegionId(regionId);
        Example<RegionInstanceModel> example = Example.of(regionInstanceModel);
        RegionInstanceModel regionInstance = regionInstanceRepository.findOne(example).get();
        ReturnVo<RegionInstanceModel> regionInstanceModelReturnVo = new ReturnVo<>(ReturnCode.SUCCESS,"查询成功",regionInstance);
        return regionInstanceModelReturnVo;
    }

    @PostMapping("executeRegion")
    public ReturnVo<Object> executeRegion(@RequestBody CalculateParamVo param) throws Exception {
        Object calculateInstance = calculateService.getCalculateInstance(param);
        ReturnVo<Object> regionModelReturnVo = new ReturnVo<>();
        regionModelReturnVo.setData(calculateInstance);
        regionModelReturnVo.setMsg("查询成功");
        regionModelReturnVo.setCode(ReturnCode.SUCCESS);
        return regionModelReturnVo;
    }


}
