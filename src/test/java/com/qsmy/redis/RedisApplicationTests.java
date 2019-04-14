package com.qsmy.redis;

import com.qsmy.redis.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

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

}
