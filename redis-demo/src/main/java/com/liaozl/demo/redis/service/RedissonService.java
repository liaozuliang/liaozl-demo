package com.liaozl.demo.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/30 11:00
 */
@Slf4j
@Service
public class RedissonService {

    @Autowired
    private RedissonClient redissonClient;

    public void testLock() {
        RLock rLock = redissonClient.getLock("RECOMMENDER_LOCK:myTestLock");
        rLock.lock();

        log.info("============testRedissonLock===== lock success");
        try {
            Thread.sleep(10*1000);
        } catch (Exception e) {
            log.error("============testRedissonLock===== error:", e);
        } finally {
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                log.info("============testRedissonLock===== unlock success");
            }
        }
    }
}
