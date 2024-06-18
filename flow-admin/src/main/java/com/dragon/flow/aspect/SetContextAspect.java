package com.dragon.flow.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.dragon.flow.config.BaseContext;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SetContextAspect {

    @Pointcut("execution(* com.dragon.flow.web.resource.customer.*.*(..))")
    public void MethodPointCut(){
    }

    @Before("MethodPointCut()")
    public void BeforePointCut(){
        String loginId = StpUtil.getLoginIdAsString();
        BaseContext.setCurrentId(loginId);
    }

    @After("MethodPointCut()")
    public void AfterPointCut(){
        BaseContext.remove();
    }
}
