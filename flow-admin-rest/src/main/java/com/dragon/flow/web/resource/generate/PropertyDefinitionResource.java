package com.dragon.flow.web.resource.generate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.model.generate.PropertyDefinition;
import com.dragon.flow.service.generate.PropertyDefinitionService;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flow/generate/property")
public class PropertyDefinitionResource {

    @Autowired
    private PropertyDefinitionService propertyDefinitionService;

    @GetMapping("getPropertiesById/{id}")
    public ReturnVo<List<PropertyDefinition>> getPropertiesById(@PathVariable String id){
        QueryWrapper<PropertyDefinition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",id);
        List<PropertyDefinition> list = propertyDefinitionService.list(queryWrapper);
        ReturnVo<List<PropertyDefinition>> listReturnVo = new ReturnVo<>();
        listReturnVo.setCode(ReturnCode.SUCCESS);
        listReturnVo.setMsg("查询成功");
        listReturnVo.setData(list);
        return listReturnVo;
    }

}
