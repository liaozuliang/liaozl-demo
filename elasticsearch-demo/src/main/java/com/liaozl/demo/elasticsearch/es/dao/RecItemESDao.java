package com.liaozl.demo.elasticsearch.es.dao;

import com.liaozl.demo.elasticsearch.es.entity.RecItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/22 16:12
 */
public interface RecItemESDao extends ElasticsearchRepository<RecItem, String> {

    List<RecItem> findByReadCountBetween(int min, int max);

    List<RecItem> findByIsViewed(boolean isViewed);

    List<RecItem> findByIsViewedAndReadCountBetween(boolean isViewed, int minReadCount, int maxReadCount);

    List<RecItem> findByReadCountIn(List<Integer> list);

    List<RecItem> findByTitleLike(String keyword);

    List<RecItem> findByKewordListContains(String keyword);

    List<RecItem> findByTitleStartingWith(String keyword);

    List<RecItem> findByTitleEndingWith(String keyword);

    List<RecItem> findByNewsIdOrderByReadCountDesc(long newsId);

    List<RecItem> findByCreateTimeBefore(long date);

    List<RecItem> findByUpdateTimeBefore(String date);
}
