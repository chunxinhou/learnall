package com.learn.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author houchunxin
 */
@Configuration
public class RedissonConfig {

    @Value("${redis.address}")
    private String redisAddress;

    @Bean(name = "address")
    public String address(){
        return redisAddress;
    }
/*

    @Bean(name = "client")
    public RedissonClient buildRedissonConfig(@Autowired Config config){
        RedissonClient redissonClient = Redisson.create(config);
        return  redissonClient;
    }


*/

@Bean(name = "config")
public Config buildRedissonConfig(){
    Assert.notNull(redisAddress,"#redis.address is requested");
    Config config = new Config();
    config.setTransportMode(TransportMode.NIO);
    String[] addressArray = redisAddress.split(";");
    config.useClusterServers().addNodeAddress(addressArray).setScanInterval(5000);
    config.setCodec(new JsonJacksonCodec());
    config.setLockWatchdogTimeout(30000);
    config.setKeepPubSubOrder(true);


    return config;
}

}
