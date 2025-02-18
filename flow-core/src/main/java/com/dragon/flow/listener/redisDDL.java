package com.dragon.flow.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class redisDDL extends KeyExpirationEventMessageListener {

    @Autowired
    

    public redisDDL(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("过期key:" + message.toString());
        log.info("过期key:" + message.toString());
        String[] split = message.toString().split("-");
        //这个地方可以根据自己的业务来监听你锁需要的key
        if(split[2]=="0"){
        //奖品，先去奖品表查询当前物品是否是定时上下架的状态，如果是，就执行命令，并且修改物品的状态，如果不是就不处理。交给消息队列处理

        }else{
        //活动，同理
        }
    }

}
