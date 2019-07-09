package com.liaozl.demo.elasticsearch.es.dao;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.elasticsearch.es.entity.EsBaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


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

    public <T extends EsBaseEntity> String update(Class<T> tClass, Map<String, Object> dataMap, String id) {
        if (StringUtils.isEmpty(id) || dataMap == null || dataMap.size() == 0 || tClass == null) {
            return null;
        }

        UpdateQuery updateQuery = new UpdateQuery();
        updateQuery.setId(id);
        updateQuery.setClazz(tClass);

        UpdateRequest updateRequest = new UpdateRequest().doc(dataMap);
        updateQuery.setUpdateRequest(updateRequest);

        UpdateResponse response = elasticsearchTemplate.update(updateQuery);
        log.info("===========update============{}", JSON.toJSONString(response));
        return response.getId();
    }
}
