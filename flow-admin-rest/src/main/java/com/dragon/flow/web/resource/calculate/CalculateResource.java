package com.dragon.flow.web.resource.calculate;


import com.dragon.flow.model.calculate.CalculateModel;
import com.dragon.flow.model.calculate.RegionModel;
import com.dragon.flow.model.customer.Activity;
import com.dragon.flow.service.calculate.CalculateService;
import com.dragon.flow.service.calculate.ModelsRepository;
import com.dragon.flow.service.calculate.RegionRepository;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/reload")
//    public ReturnVo<String> reload() throws Exception {
//        droolsConfig.refreshBean();
//        ReturnVo<String> stringReturnVo = new ReturnVo<>();
//        stringReturnVo.setCode(ReturnCode.SUCCESS);
//        stringReturnVo.setMsg("重载成功");
//        return stringReturnVo;
//    }

    @PostMapping("getResult")
    public ReturnVo<Object> getCalculateInstance(@RequestBody Object param,@RequestParam String typeName) throws Exception {
        Object calculateInstance = calculateService.getCalculateInstance(param, typeName);
        ReturnVo<Object> calculateReturnVo = new ReturnVo<>();
        calculateReturnVo.setCode(ReturnCode.SUCCESS);
        calculateReturnVo.setData(calculateInstance);
        calculateReturnVo.setMsg("计算成功");
        return calculateReturnVo;
    }

    @Transactional
    @PostMapping("saveModel")
    public ReturnVo saveModel(@RequestBody CalculateModel calculateModel){
        List<RegionModel> regionModelList = calculateModel.getTemplate();
        CalculateModel insert = modelsRepository.insert(calculateModel);
        String id = insert.getId();
        regionModelList.forEach(item->{
            item.setModelId(id);
        });
        regionRepository.saveAll(regionModelList);
        ReturnVo<Object> returnVo = new ReturnVo<>();
        returnVo.setCode(ReturnCode.SUCCESS);
        returnVo.setData(null);
        returnVo.setMsg("保存成功");
        return returnVo;
    }

    @PostMapping("getModels")
    public ReturnVo<List<CalculateModel>> getModels(@RequestBody ParamVo<Activity> param){
        int pageNum = param.getQuery().getPageNum();
        int pageSize = param.getQuery().getPageSize();
        System.out.println("num"+pageNum);
        System.out.println("size"+pageSize);
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
        CalculateModel calculateModel = modelsRepository.findById(id).get();
        List<RegionModel> info = regionRepository.findByModelId(id);
        calculateModel.setTemplate(info);
        ReturnVo<CalculateModel> calculateModelReturnVo = new ReturnVo<>();
        calculateModelReturnVo.setMsg("查询成功");
        calculateModelReturnVo.setCode(ReturnCode.SUCCESS);
        calculateModelReturnVo.setData(calculateModel);
        return calculateModelReturnVo;
    }




}
