package com.dragon.flow.web.resource.test;

import com.dragon.flow.model.customer.Goods;
import com.dragon.flow.model.test.Calculate;
import com.dragon.flow.vo.pager.ParamVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow/test/calculate")
public class CalculateResource {

//    @Autowired
//    private KieContainer kieContainer;

    @PostMapping("getAverage")
    public ReturnVo<Object> page(@RequestBody Object param){
        System.out.println("看看"+param.toString());
        KieServices kieServices = KieServices.Factory.get();
        //获取kieContainer
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("simpleRuleKSession");
        kieSession.insert(param);
        kieSession.fireAllRules();
        kieSession.dispose();
        ReturnVo<Object> calculateReturnVo = new ReturnVo<>();
        calculateReturnVo.setCode(ReturnCode.SUCCESS);
        calculateReturnVo.setData(param);
        calculateReturnVo.setMsg("计算成功");
        return calculateReturnVo;
    }

}
