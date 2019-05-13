package com.learn.redisson.manager;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author houchunxin
 */
@Component
public class RedissonManager {

    @Autowired
    private String address;
    private RedissonClient redissonClient;
    @Autowired
    private Config config;

    @PostConstruct
    public void init(){
        RedissonClient redissonClient = Redisson.create(config);
        this.redissonClient = redissonClient;
    }
    @PreDestroy
    public void destroy(){
        if (redissonClient != null){
            redissonClient.shutdown();
        }
    }


    public String getAddress() {
        return address;
    }


    public RedissonClient getRedissonClient() {
        return redissonClient;
    }



}
