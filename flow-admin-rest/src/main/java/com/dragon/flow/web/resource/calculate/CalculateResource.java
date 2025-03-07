package com.dragon.flow.web.resource.calculate;

import com.dragon.flow.config.DroolsConfig;
import com.dragon.flow.config.modeler.KieUtilClass;
import com.dragon.flow.service.calculate.CalculateService;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.beanutils.BeanUtils;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/flow/calculate")
public class CalculateResource {

    @Autowired
    private CalculateService calculateService;

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








}
