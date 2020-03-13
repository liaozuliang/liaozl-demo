package com.liaozl.demo.drools;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.drools.entity.HotNews;
import com.liaozl.demo.drools.entity.TopNews;
import com.liaozl.demo.drools.enums.NewsTypeEnum;
import com.liaozl.demo.drools.enums.SortTypeEnum;
import com.liaozl.demo.drools.service.CalculateService;
import com.liaozl.demo.drools.service.DynamicRuleTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DroolsDemoApplicationTests {

    @Autowired
    private KieSession kieSession;

    @Autowired
    private CalculateService calculateService;

    @Autowired
    private DynamicRuleTestService dynamicRuleTestService;

    @Test
    public void testDynamicRule() {
        dynamicRuleTestService.testDynamicRule();
    }

    @Test
    public void testHotNewsRules() {
        kieSession.setGlobal("calculateService", calculateService);

        Globals globals = kieSession.getGlobals();
        log.info("GlobalKeys======" + globals.getGlobalKeys().toString());

        HotNews hotNews = new HotNews();
        hotNews.setNewsType(NewsTypeEnum.HOT);
        hotNews.setSortType(SortTypeEnum.ASC);

        HotNews hotNews2 = new HotNews();
        hotNews2.setNewsType(NewsTypeEnum.HOT);
        hotNews2.setSortType(SortTypeEnum.DESC);

        kieSession.insert(hotNews);
        kieSession.insert(hotNews2);

        int successCount = kieSession.fireAllRules();
        log.info("======共触发{}条规则=====", successCount);

        log.info("分数计算后的hotNews：{}", JSON.toJSONString(hotNews));
        log.info("分数计算后的hotNews2：{}", JSON.toJSONString(hotNews2));
    }

    @Test
    public void testTopNewsRules() {
        kieSession.setGlobal("calculateService", calculateService);

        Globals globals = kieSession.getGlobals();
        log.info("GlobalKeys======" + globals.getGlobalKeys().toString());

        TopNews topNews1 = new TopNews();
        topNews1.setNewsType(NewsTypeEnum.TOP);
        topNews1.setSortType(SortTypeEnum.ASC);
        topNews1.setCount(-1);
        topNews1.setScore(-1);

        TopNews topNews2 = new TopNews();
        topNews2.setNewsType(NewsTypeEnum.TOP);
        topNews2.setSortType(SortTypeEnum.ASC);
        topNews2.setCount(10);
        topNews2.setScore(10);

        TopNews topNews3 = new TopNews();
        topNews3.setNewsType(NewsTypeEnum.TOP);
        topNews3.setSortType(SortTypeEnum.ASC);
        topNews3.setCount(160);
        topNews3.setScore(16);

        TopNews topNews4 = new TopNews();
        topNews4.setNewsType(NewsTypeEnum.TOP);
        topNews4.setSortType(SortTypeEnum.DESC);
        topNews4.setCount(15);
        topNews4.setScore(15);

        TopNews topNews5 = new TopNews();
        topNews5.setNewsType(NewsTypeEnum.TOP);
        topNews5.setSortType(SortTypeEnum.DESC);
        topNews5.setCount(228);
        topNews5.setScore(228);

        TopNews topNews6 = new TopNews();
        topNews6.setNewsType(NewsTypeEnum.TOP);
        topNews6.setSortType(SortTypeEnum.DESC);
        topNews6.setCount(436);
        topNews6.setScore(436);

        kieSession.insert(topNews1);
        kieSession.insert(topNews2);
        kieSession.insert(topNews3);
        kieSession.insert(topNews4);
        kieSession.insert(topNews5);
        kieSession.insert(topNews6);

        int successCount = kieSession.fireAllRules();
        log.info("======共触发{}条规则=====", successCount);

        log.info("分数计算后的topNews1：{}", JSON.toJSONString(topNews1));
        log.info("分数计算后的topNews2：{}", JSON.toJSONString(topNews2));
        log.info("分数计算后的topNews3：{}", JSON.toJSONString(topNews3));
        log.info("分数计算后的topNews4：{}", JSON.toJSONString(topNews4));
        log.info("分数计算后的topNews5：{}", JSON.toJSONString(topNews5));
        log.info("分数计算后的topNews6：{}", JSON.toJSONString(topNews6));
    }

}
