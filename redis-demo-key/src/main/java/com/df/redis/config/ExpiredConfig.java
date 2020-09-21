package com.df.redis.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName ExpiredConfig
 * @date 2020/9/14  10:24
 */
@Component
public class ExpiredConfig extends KeyExpirationEventMessageListener {

    public ExpiredConfig(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("listener redis Expired key" + message.toString());
    }
}
