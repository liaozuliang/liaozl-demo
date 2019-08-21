package com.liaozl.demo.hbase;

import com.liaozl.demo.hbase.service.HBaseDaoTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/21 9:10
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HBaseDaoTestServiceTest {

    @Autowired
    private HBaseDaoTestService hBaseDaoTestService;


    @Test
    public void testAdd() {
        hBaseDaoTestService.testAdd();
    }

    @Test
    public void testQuery() {
        hBaseDaoTestService.testQuery();
    }

    @Test
    public void testDelete() {
        hBaseDaoTestService.testDelete();
    }

}
