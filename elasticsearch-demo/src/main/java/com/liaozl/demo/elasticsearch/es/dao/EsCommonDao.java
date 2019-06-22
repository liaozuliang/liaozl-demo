package com.liaozl.demo.elasticsearch.es.dao;

import com.liaozl.demo.elasticsearch.es.entity.EsBaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/22 15:10
 */
@Slf4j
@Service
public class EsCommonDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public <T extends EsBaseEntity> void createIndex(Class<T> tClass) {
        elasticsearchTemplate.createIndex(tClass);
        elasticsearchTemplate.putMapping(tClass);
    }

    public <T extends EsBaseEntity> void deleteIndex(Class<T> tClass) {
        elasticsearchTemplate.deleteIndex(tClass);
    }
}
