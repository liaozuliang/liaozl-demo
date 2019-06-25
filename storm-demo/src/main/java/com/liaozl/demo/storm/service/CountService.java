package com.liaozl.demo.storm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 10:44
 */
@Slf4j
@Service
public class CountService implements Serializable {

    private ConcurrentHashMap<Integer, Integer> userId_count_map = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Integer> pageId_count_map = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> ip_count_map = new ConcurrentHashMap<>();

    public void countByUserId(int userId, String userName) {
        int count = 1;
        if (userId_count_map.containsKey(userId)) {
            count += userId_count_map.get(userId);
        }

        userId_count_map.put(userId, count);
        log.info("===用户浏览次数统计: userId={}, userName, count={}", userId, userName, count);
    }

    public void countByPageId(int pageId) {
        int count = 1;
        if (pageId_count_map.containsKey(pageId)) {
            count += pageId_count_map.get(pageId);
        }

        pageId_count_map.put(pageId, count);
        log.info("===网页浏览次数统计: pageId={}, count={}", pageId, count);
    }

    public void countByIp(String ip) {
        int count = 1;
        if (ip_count_map.containsKey(ip)) {
            count += ip_count_map.get(ip);
        }

        ip_count_map.put(ip, count);
        log.info("===Ip浏览次数统计: ip={}, count={}", ip, count);
    }

}
