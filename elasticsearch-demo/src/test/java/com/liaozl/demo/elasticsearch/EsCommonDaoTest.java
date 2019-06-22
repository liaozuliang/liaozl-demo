package com.liaozl.demo.elasticsearch;

import com.liaozl.demo.elasticsearch.es.dao.EsCommonDao;
import com.liaozl.demo.elasticsearch.es.entity.RecItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/22 15:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsCommonDaoTest {

    @Autowired
    private EsCommonDao esCommonDao;

    @Test
    public void testCreateIndex() {
        esCommonDao.createIndex(RecItem.class);
    }

    @Test
    public void testDeleteIndex() {
        esCommonDao.deleteIndex(RecItem.class);
    }
}
