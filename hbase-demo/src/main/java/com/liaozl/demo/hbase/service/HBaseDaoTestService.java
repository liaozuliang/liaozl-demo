package com.liaozl.demo.hbase.service;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.hbase.dao.ColumnData;
import com.liaozl.demo.hbase.dao.HBaseDao;
import com.liaozl.demo.hbase.dao.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/21 10:02
 */
@Slf4j
@Service
public class HBaseDaoTestService {

    @Autowired
    private HBaseDao hBaseDao;

    static String tableName = "t_user";
    static String familyName1 = "baseInfo";
    static String familyName2 = "extInfo";

    public void testAdd() {
        if (!hBaseDao.tableExists(tableName)) {
            hBaseDao.createTable(tableName, Arrays.asList(new String[]{familyName1, familyName2}));
        }

        List<ColumnData> columnDataList = new ArrayList<>();
        columnDataList.add(new ColumnData(familyName1, "name", "张三"));
        columnDataList.add(new ColumnData(familyName1, "sex", "男"));
        columnDataList.add(new ColumnData(familyName1, "age", "26"));
        columnDataList.add(new ColumnData(familyName2, "image", "htpp://www.baidu.com"));
        columnDataList.add(new ColumnData(familyName2, "address", "北京"));

        hBaseDao.putRow(tableName, "zhangsan_nan_26_beijing", columnDataList);
        hBaseDao.putRow(tableName, "zhang1san1_nan_26_beijing", columnDataList);

        columnDataList = new ArrayList<>();
        columnDataList.add(new ColumnData(familyName1, "name", "张三"));
        columnDataList.add(new ColumnData(familyName1, "sex", "女"));
        columnDataList.add(new ColumnData(familyName1, "age", "36"));
        columnDataList.add(new ColumnData(familyName2, "image", "htpp://www.baidu.com"));
        columnDataList.add(new ColumnData(familyName2, "address", "南京"));

        hBaseDao.putRow(tableName, "zhangsan_nv_36_nanjing", columnDataList);
        hBaseDao.putRow(tableName, "zhangsan_nan_36_nanjing", columnDataList);
    }

    public void testQuery() {
        User user = hBaseDao.getRow(tableName, "zhangsan_nan_26_beijing", User.class);
        log.info("=========user========={}", JSON.toJSONString(user));

        List<User> userList = hBaseDao.scanRows(tableName, "zhangsan", User.class);
        log.info("=========userList========={}", JSON.toJSONString(userList));

        List<User> userList2 = hBaseDao.scanRows(tableName, User.class);
        log.info("=========userList2========={}", JSON.toJSONString(userList2));

        List<ColumnData> equalColumnConditions = new ArrayList<>();
        equalColumnConditions.add(new ColumnData(familyName1, "name", "张三"));

        List<User> userList3 = hBaseDao.scanRows(tableName, equalColumnConditions, User.class);
        log.info("=========userList3========={}", JSON.toJSONString(userList3));

        equalColumnConditions.add(new ColumnData(familyName2, "address", "南京"));
        List<User> userList4 = hBaseDao.scanRows(tableName, equalColumnConditions, User.class);
        log.info("=========userList4========={}", JSON.toJSONString(userList4));

        List<User> pageList1 = hBaseDao.scanRows(tableName, null, 10, User.class);
        log.info("=========pageList1========={}", JSON.toJSONString(pageList1));

        List<User> pageList2 = hBaseDao.scanRows(tableName, "zhangsan_nan_36_nanjing", 10, User.class);
        log.info("=========pageList2========={}", JSON.toJSONString(pageList2));


    }

    public void testDelete() {
        hBaseDao.deleteRow(tableName, "zhangsan_nv_36_nanjing");
    }
}
