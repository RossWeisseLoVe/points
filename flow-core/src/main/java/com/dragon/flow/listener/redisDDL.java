package com.dragon.flow.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class redisDDL extends KeyExpirationEventMessageListener {


    public redisDDL(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("过期key:" + message.toString());
        //这个地方可以根据自己的业务来监听你锁需要的key
    }

}
