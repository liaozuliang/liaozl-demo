package com.liaozl.demo.redis.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/31 12:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonServiceTest {

    @Autowired
    private RedissonService redissonService;

    @Test
    public void testLock() {
        redissonService.testLock();
    }
}
