package com.learn.redisson.controller;

import com.learn.redisson.bean.UserInfo;
import com.learn.redisson.manager.RedissonManager;

import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/redisson")
public class RedissonController {
    @Autowired
    private RedissonManager manager;

    @RequestMapping("/keys")
    public @ResponseBody Iterable<?> getKeys(){
        RKeys keys = manager.getRedissonClient().getKeys();
        System.out.println(keys.randomKey());
       // System.out.println(keys.getKeys());
       // System.out.println(keys.getKeys(10));
        System.out.println(keys.count());
        return null;
    }

    @RequestMapping("/getBucket")
    public @ResponseBody UserInfo getBucket(){
        RBucket<UserInfo> bucket = manager.getRedissonClient().getBucket("候春鑫", new JsonJacksonCodec());
        UserInfo info = new UserInfo();
        info.setAge(20);
        info.setEmail("123@qq.com");
        info.setName("houchuxnin");
        info.setNike("hou");
        info.setPhone(1234567890);


        UserInfo info2 = new UserInfo();
        info2.setAge(25);
        info2.setEmail("123@qq.com");
        info2.setName("houchuxnin");
        info2.setNike("hou");
        info2.setPhone(1234567890);

        //bucket.set(info);
        bucket.compareAndSet(info,info2);

        return bucket.get();
    }

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.set(Calendar.HOUR_OF_DAY,0000);
        cal.set(Calendar.MILLISECOND, 0000);
        cal.set(Calendar.MINUTE,0000);
        System.out.printf(new Date(cal.getTimeInMillis()).getTime()+"");

    }
}
