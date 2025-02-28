package com.dragon.flow.web.resource.generate;

import com.dragon.flow.model.generate.ClassDefinition;
import com.dragon.flow.service.generate.ClassDefinitionService;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flow/generate/class")
public class ClassDefinitionResource {

    @Autowired
    private ClassDefinitionService classDefinitionService;

    @GetMapping("/getAllRules")
    public ReturnVo<List<ClassDefinition>> getAllRules(){
        List<ClassDefinition> list = classDefinitionService.list();
        ReturnVo<List<ClassDefinition>> listReturnVo = new ReturnVo<>();
        listReturnVo.setMsg("查询成功");
        listReturnVo.setData(list);
        listReturnVo.setCode(ReturnCode.SUCCESS);
        return listReturnVo;
    }

}
