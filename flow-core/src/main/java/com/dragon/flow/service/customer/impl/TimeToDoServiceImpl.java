package com.dragon.flow.service.customer.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.flow.model.customer.TimeToDo;
import com.dragon.flow.service.customer.TimeToDoService;
import com.dragon.flow.mapper.customer.TimeToDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
* @author IRDC-Front
* @description 针对表【tbl_customer_time_to_do】的数据库操作Service实现
* @createDate 2024-08-02 18:22:44
*/
@Service
public class TimeToDoServiceImpl extends ServiceImpl<TimeToDoMapper, TimeToDo>
    implements TimeToDoService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private  TimeToDoMapper timeToDoMapper;

    /**
     * 获取所有的区域
     * @param id
     * @param type 0是奖品 1是活动
     * @param time key过期时间
     * @param timeType 开始时间或者结束时间
     * @return
     */
    @Override
    public void sendDelayedTask(String id, Integer type, Date time,String timeType) {
        Date now = new Date();
        long diff = time.getTime() - now.getTime();
        long diffSeconds = diff/1000;
        redisTemplate.opsForValue().set(id +'-'+ timeType + '-' + type,diff,diffSeconds, TimeUnit.SECONDS);
    }

}




