package com.df.redis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @date 2020/9/14  10:27
 */
@RestController
public class TestController {


    @Autowired
    private RedisTemplate redisTemplate;



    @GetMapping("setRedis/{key}/{time}")
    public String setRedis(@PathVariable("key") String key, @PathVariable("time") Long time) {
        redisTemplate.opsForValue().set(key, "123", time, TimeUnit.SECONDS);
        return "";
    }

    @GetMapping("getRedis/{key}")
    public Object getRedis(@PathVariable("key") String key) {
        Object o = redisTemplate.opsForValue().get(key);
        return o;
    }

}
