package com.learn.redisson.controller;

import com.learn.redisson.manager.RedissonManager;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author houchunxin
 */
@Controller
@RequestMapping("/redisson")
public class HelloWorld {
    @Autowired
    private RedissonManager redissonManager;

    public static final SimpleDateFormat format_y_m_d = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("hello")
    public @ResponseBody String hello() throws InterruptedException {
        RedissonClient redissonClient = redissonManager.getRedissonClient();
        RAtomicLong atomicLong = redissonClient.getAtomicLong("key-atomicLong");
        atomicLong.expire(20L, TimeUnit.SECONDS);
        long l = atomicLong.addAndGet(2);
        boolean b = atomicLong.compareAndSet(70, 0);
        atomicLong.decrementAndGet();
        atomicLong.get();
        atomicLong.incrementAndGet();
        RFuture<Boolean> booleanRFuture = atomicLong.compareAndSetAsync(2,4);

        System.out.printf(String.valueOf(b));
        return String.valueOf(l);
    }

    public static void main(String[] args) throws ParseException {
        for (;;){

                Date today = format_y_m_d.parse(format_y_m_d.format(new Date()));
                System.out.println(today.getTime()+"");

        }

    }

}
