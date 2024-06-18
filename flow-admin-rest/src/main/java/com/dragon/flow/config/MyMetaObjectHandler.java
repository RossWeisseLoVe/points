package com.dragon.flow.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void insertFill(MetaObject metaObject) {
        String id = BaseContext.getCurrentId();
//        log.info(Thread.currentThread().getName());
//        System.out.println("BaseContext.getCurrentId(): "+ BaseContext.getCurrentId());
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateTime", new Date());
        metaObject.setValue("creator",id);
        metaObject.setValue("updator",id);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updator",BaseContext.getCurrentId());
        metaObject.setValue("updateTime", new Date());
    }

}
