package com.liaozl.demo.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.elasticsearch.dto.Channel;
import com.liaozl.demo.elasticsearch.es.dao.RecItemESDao;
import com.liaozl.demo.elasticsearch.es.entity.RecItem;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/22 16:12
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecItemESDaoTest {

    @Autowired
    private RecItemESDao recItemESDao;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testSave() {
        RecItem item = new RecItem();

        item.setId("1");
        item.setItemId("1");
        item.setNewsId(1L);
        item.setTitle("传承千年的哑神舞，神秘传说回归于真本");
        item.setKeywords("彝族,文化,民俗,云南,艺术,历史,摄影,祥云县,弥渡,白族,昆明,宾川,姚安县,苗族,芦笙");
        item.setKewordList(Arrays.asList(new String[]{"彝族", "文化", "民俗", "云南", "艺术", "历史", "摄影", "祥云县", "弥渡", "白族", "昆明", "宾川", "姚安县", "苗族", "芦笙"}));

        List<Channel> channelList = new ArrayList<>(2);
        channelList.add(new Channel(1, "国际"));
        channelList.add(new Channel(2, "历史"));
        item.setChannelList(channelList);

        item.setReadCount(123);
        item.setIsViewed(false);
        item.setUrl("https://www.baidu.com/");
        item.setLocation(new GeoPoint(39.570178147550735, 117.28976716015623));
        item.setCreateTime(new Date());

        recItemESDao.save(item); // 先删除后新增

        //================
        List<RecItem> recItemList = new ArrayList<>(2);

        item = new RecItem();

        item.setId("2");
        item.setItemId("2");
        item.setNewsId(1L);
        item.setTitle("2传承千年的哑神舞，神秘传说回归于真本");
        item.setKeywords("彝族,文化,民俗,云南,艺术,历史,摄影,祥云县,弥渡,白族,昆明,宾川,姚安县,苗族,芦笙");
        item.setKewordList(Arrays.asList(new String[]{"彝族", "文化", "民俗", "云南", "艺术", "历史", "摄影", "祥云县", "弥渡", "白族", "昆明", "宾川", "姚安县", "苗族", "芦笙"}));

        channelList = new ArrayList<>(2);
        channelList.add(new Channel(1, "娱乐"));
        channelList.add(new Channel(2, "小视频"));
        item.setChannelList(channelList);

        item.setReadCount(50);
        item.setIsViewed(false);
        item.setUrl("https://www.baidu.com/");
        item.setLocation(new GeoPoint(39.510178147550735, 117.29976716015623));
        item.setCreateTime(new Date());

        recItemList.add(item);

        item = new RecItem();

        item.setId("3");
        item.setItemId("1");
        item.setNewsId(1L);
        item.setTitle("3传承千年的哑神舞，神秘传说回归于真本");
        item.setKeywords("彝族,文化,民俗,云南,艺术,历史,摄影,祥云县,弥渡,白族,昆明,宾川,姚安县,苗族,芦笙");
        item.setKewordList(Arrays.asList(new String[]{"彝族", "文化", "民俗", "云南", "艺术", "历史", "摄影", "祥云县", "弥渡", "白族", "昆明", "宾川", "姚安县", "苗族", "芦笙"}));

        channelList = new ArrayList<>(2);
        channelList.add(new Channel(3, "军事"));
        channelList.add(new Channel(4, "体育"));
        item.setChannelList(channelList);

        item.setReadCount(6);
        item.setIsViewed(true);
        item.setUrl("https://www.baidu.com/");
        item.setLocation(new GeoPoint(40.570178147550735, 118.28976716015623));
        item.setCreateTime(new Date());

        recItemList.add(item);

        recItemESDao.saveAll(recItemList);
    }

    @Test
    public void testQuery() {
        Iterable<RecItem> recItemIterable = recItemESDao.findAll();
        log.info("====testQuery===findAll===={}", JSON.toJSONString(recItemIterable.iterator()));

        List<RecItem> recItemList = recItemESDao.findByReadCountBetween(1, 55);
        log.info("====testQuery===findByReadCountBetween===={}", JSON.toJSONString(recItemList));

        recItemList = recItemESDao.findByIsViewed(false);
        log.info("====testQuery===findByIsViewed===={}", JSON.toJSONString(recItemList));

        RecItem recItem = recItemESDao.findById("1").orElse(null);
        log.info("====testQuery===findById===={}", JSON.toJSONString(recItem));

        recItemList = recItemESDao.findByIsViewedAndReadCountBetween(false, 1, 6);
        log.info("====testQuery===findByIsViewedAndReadCountBetween===={}", JSON.toJSONString(recItemList));

        recItemList = recItemESDao.findByReadCountIn(Arrays.asList(new Integer[]{6, 50}));
        log.info("====testQuery===findByReadCountIn===={}", JSON.toJSONString(recItemList));

        recItemList = recItemESDao.findByKewordListContains("云南");
        log.info("====testQuery===findByKewordListContains===={}", JSON.toJSONString(recItemList));

        recItemList = recItemESDao.findByTitleEndingWith("神秘传说回归于真本");
        log.info("====testQuery===findByTitleEndingWith===={}", JSON.toJSONString(recItemList));

        recItemList = recItemESDao.findByTitleStartingWith("3传承");
        log.info("====testQuery===findByTitleStartingWith===={}", JSON.toJSONString(recItemList));

        recItemList = recItemESDao.findByTitleLike("神秘传说");
        log.info("====testQuery===findByTitleLike===={}", JSON.toJSONString(recItemList));

        // 分词匹配
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "神秘传说");
        recItemIterable = recItemESDao.search(queryBuilder);
        log.info("====testQuery===MatchQueryBuilder===={}", JSON.toJSONString(recItemIterable.iterator()));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("isViewed", false)); // 完全匹配
        nativeSearchQueryBuilder.withPageable(PageRequest.of(0, 2));// 分页查询，第一页，每页2条
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("readCount").order(SortOrder.DESC)); //排序

        String[] returnFields = {"id", "title"};
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(returnFields, null)); // 指定返回字段

        Page<RecItem> recItemPage = recItemESDao.search(nativeSearchQueryBuilder.build());
        log.info("====testQuery===pageQuery==页数：{}，总数据：{}=={}", recItemPage.getTotalPages(), recItemPage.getTotalElements(), JSON.toJSONString(recItemPage));


        // 分组查询
        nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(null, null)); // 不查询任何结果
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("isViewedGroup").field("isViewed"));

        AggregatedPage<RecItem> aggregatedPage = (AggregatedPage<RecItem>) recItemESDao.search(nativeSearchQueryBuilder.build());
        LongTerms buckets = (LongTerms) aggregatedPage.getAggregation("isViewedGroup");
        for (LongTerms.Bucket bucket : buckets.getBuckets()) {
            log.info("====testQuery===groupQuery=={}:{}", bucket.getKeyAsString(), bucket.getDocCount());
        }

        // 分组求平均值查询
        nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(null, null)); // 不查询任何结果
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("isViewedGroup").field("isViewed")
                .subAggregation(AggregationBuilders.avg("readCountAvgGroup").field("readCount")));

        aggregatedPage = (AggregatedPage<RecItem>) recItemESDao.search(nativeSearchQueryBuilder.build());
        buckets = (LongTerms) aggregatedPage.getAggregation("isViewedGroup");
        for (LongTerms.Bucket bucket : buckets.getBuckets()) {
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("readCountAvgGroup");
            log.info("====testQuery===groupAvgQuery==isViewed:{}, count:{}, readCountAvg:{}", bucket.getKeyAsString(), bucket.getDocCount(), avg.getValue());
        }

        // 分组求平均值查询2
        nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(null, null)); // 不查询任何结果
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("isViewedGroup").field("isViewed")
                .subAggregation(AggregationBuilders.avg("readCountAvgGroup").field("readCount")));

        aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), RecItem.class);
        buckets = (LongTerms) aggregatedPage.getAggregation("isViewedGroup");
        for (LongTerms.Bucket bucket : buckets.getBuckets()) {
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("readCountAvgGroup");
            log.info("====testQuery===groupAvgQuery2==isViewed:{}, count:{}, readCountAvg:{}", bucket.getKeyAsString(), bucket.getDocCount(), avg.getValue());
        }

        recItemList = recItemESDao.findByNewsIdOrderByReadCountDesc(1);
        log.info("====testQuery===findByNewsIdOrderbOrderByReadCountDesc===={}", JSON.toJSONString(recItemList));

        recItemIterable = recItemESDao.findAll(Sort.by(Sort.Order.asc("isViewed"), Sort.Order.desc("readCount")));
        log.info("====testQuery===findAllSort===={}", JSON.toJSONString(recItemIterable.iterator()));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        recItemList = recItemESDao.findByCreateTimeBefore(calendar.getTime().getTime());
        log.info("====testQuery===findByCreateTimeBefore===={}", JSON.toJSONString(recItemList));

        Criteria criteria = Criteria.where("isViewed").is("false");
        criteria.and(Criteria.where("channelList.name").is("国际"));

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        criteriaQuery.addSort(Sort.by(Sort.Order.asc("isViewed")));
        criteriaQuery.addSort(Sort.by(Sort.Order.desc("readCount")));
        criteriaQuery.addSourceFilter(new FetchSourceFilter(returnFields, null));
        criteriaQuery.setPageable(PageRequest.of(0, 10));

        recItemList = elasticsearchTemplate.queryForList(criteriaQuery, RecItem.class);
        log.info("====testQuery===criteriaQuery===={}", JSON.toJSONString(recItemList));

        // 地理位置

        // 数组字段包含
    }
}
