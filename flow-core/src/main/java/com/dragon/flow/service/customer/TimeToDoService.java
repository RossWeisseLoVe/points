package com.dragon.flow.service.customer;

import com.dragon.flow.model.customer.TimeToDo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_time_to_do】的数据库操作Service
* @createDate 2024-08-02 18:22:44
*/
public interface TimeToDoService extends IService<TimeToDo> {


    void sendDelayedTask(String id, Integer itemType, Date time, String timeType);

}
