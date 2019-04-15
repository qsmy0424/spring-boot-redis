package com.qsmy.redis;

import com.qsmy.redis.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() throws InterruptedException {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set("qsmy", "wwhm");
        Assert.assertEquals("wwhm", operations.get("qsmy"));
        System.out.println(operations.get("qsmy"));

        User user = new User();
        user.setName("qsmy");
        user.setAge(10);

        operations.set("user", user, 100, TimeUnit.MILLISECONDS);

        System.out.println(redisTemplate.hasKey("user"));
        Thread.sleep(1000);
        System.out.println(redisTemplate.hasKey("user"));

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        hashOperations.put("hash", "you1", "you2");
        String value = (String) hashOperations.get("hash", "you");
        System.out.println(value);
    }

    @Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("qsmy1", "wwhm1");
        listOperations.leftPush("qsmy1", "wwhm2");
        listOperations.leftPush("qsmy1", "wwhm3");
        // System.out.println(listOperations.leftPop("qsmy1"));
        List<String> list = listOperations.range("qsmy1", 0, 2);
        System.out.println("11111111111111111111111");
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("qsmy11", "wwhm1");
        setOperations.add("qsmy11", "wwhm2");
        setOperations.add("qsmy11", "wwhm3");
        setOperations.add("qsmy22", "wwhm2");

        Set difference = setOperations.difference("qsmy11", "qsmy22");
        for (Object s : difference) {
            System.out.println(s);
        }
    }

    @Test
    public void stringRedisTemplateTest() {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("abcd", "def");
        System.out.println(stringStringValueOperations.get("abcd"));
    }

    @Test
    public void testZSet() {
        String key = "zset";
        redisTemplate.delete(key);
        ZSetOperations zSet = redisTemplate.opsForZSet();
        zSet.add(key, "qsmy3", 3);
        zSet.add(key, "qsmy2", 2);
        zSet.add(key, "qsmy4", 4);
        zSet.add(key, "qsmy1", 1);

        Set<String> range = zSet.range(key, 0, 2);
        for (String s : range) {
            System.out.println(s);
        }
    }
}
