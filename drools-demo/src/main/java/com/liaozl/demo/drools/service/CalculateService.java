package com.liaozl.demo.drools.service;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.drools.entity.HotNews;
import com.liaozl.demo.drools.enums.NewsTypeEnum;
import com.liaozl.demo.drools.enums.SortTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/5/20 14:19
 */
@Slf4j
@Service
public class CalculateService {

    public int getAscCount(HotNews hotNews) {
        int count = 1;

        if (hotNews != null && NewsTypeEnum.HOT.equals(hotNews.getNewsType())) {
            if (SortTypeEnum.ASC.equals(hotNews.getSortType())) {
                count = 100;
            } else if (SortTypeEnum.DESC.equals(hotNews.getSortType())) {
                count = 200;
            } else {
                count = 50;
            }
        }

        log.info("getAscCount，count={}, hotNews={}", count, JSON.toJSONString(hotNews));

        return count;
    }

    public int getDescCount(HotNews hotNews) {
        int count = 1;

        if (hotNews != null && NewsTypeEnum.HOT.equals(hotNews.getNewsType())) {
            if (SortTypeEnum.ASC.equals(hotNews.getSortType())) {
                count = 110;
            } else if (SortTypeEnum.DESC.equals(hotNews.getSortType())) {
                count = 280;
            } else {
                count = 70;
            }
        }

        log.info("getDescCount，count={}, hotNews={}", count, JSON.toJSONString(hotNews));

        return count;
    }
}
