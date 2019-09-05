package com.liaozl.demo.hive.service;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.hive.entity.GsGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/9/4 17:16
 */
@Slf4j
@Service
public class HiveService {

    @Autowired
    @Qualifier("hiveDruidDataSource")
    private DataSource druidDataSource;

    @Autowired
    @Qualifier("hiveDruidTemplate")
    private JdbcTemplate jdbcTemplate;

    public void showDatabases() {
        List<String> list = new ArrayList<String>();
        Statement statement = null;

        try {
            statement = druidDataSource.getConnection().createStatement();
            String sql = "show databases";

            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                list.add(res.getString(1));
            }
        } catch (Exception e) {
            log.error("showDatabases error:", e);
        }

        log.info("=============showDatabases=============={}", list);
    }

    public void createTable() {
        try {
            String sql = "drop table gs_game_txt";
            jdbcTemplate.execute(sql);

            sql = "drop table gs_game";
            jdbcTemplate.execute(sql);

            sql = "create table gs_game_txt(id int, name string, download_num int) row format delimited fields terminated by '\t' STORED AS TEXTFILE";
            jdbcTemplate.execute(sql);

            /**
             * 如果要支持delete和update，则必须输出是AcidOutputFormat然后必须分桶。
             * 而且目前只有ORCFileformat支持AcidOutputFormat，不仅如此建表时必须指定参数('transactional' = true)
             */
            sql = "create table gs_game(id int, name string, download_num int) CLUSTERED BY (id) INTO 3 BUCKETS ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' STORED AS ORC LOCATION '/user/hive/gs_game/S1_AC_ACTUAL_PAYDETAIL' TBLPROPERTIES('transactional'='true')";
            jdbcTemplate.execute(sql);
            log.info("=============createTable==============");
        } catch (Exception e) {
            log.error("createTable error:", e);
        }
    }

    public void insert() {
        try {
            String sql = "insert into gs_game values(1, '我是新插入的数据', 11)";
            jdbcTemplate.execute(sql);
            log.info("=============insert==============");
        } catch (Exception e) {
            log.error("insert error:", e);
        }
    }

    public void loadData() {
        try {
            String sql = "load data local inpath '/opt/liaozl/gs_game.txt' into table gs_game_txt";
            jdbcTemplate.execute(sql);

            sql = "insert into gs_game select * from gs_game_txt";
            jdbcTemplate.execute(sql);
            log.info("=============loadData==============");
        } catch (Exception e) {
            log.error("loadData error:", e);
        }
    }

    public void count() {
        try {
            String sql = "select count(*) from gs_game";
            jdbcTemplate.execute(sql);
            log.info("=============count==============");
        } catch (Exception e) {
            log.error("count error:", e);
        }
    }

    public void queryAll() {
        try {
            String sql = "select * from gs_game";
            List<GsGame> gsGameList = jdbcTemplate.queryForList(sql, GsGame.class);
            log.info("=============queryAll=============={}", JSON.toJSONString(gsGameList));
        } catch (Exception e) {
            log.error("queryAll error:", e);
        }
    }

    public void query() {
        try {
            String sql = "select * from gs_game where id > 2100 limit 10";
            List<GsGame> gsGameList = jdbcTemplate.queryForList(sql, GsGame.class);
            log.info("=============query=============={}", JSON.toJSONString(gsGameList));
        } catch (Exception e) {
            log.error("query error:", e);
        }
    }

    public void delete() {
        try {
            String sql = "delete from gs_game where id < 2000";
            jdbcTemplate.execute(sql);
            log.info("=============delete==============");
        } catch (Exception e) {
            log.error("delete error:", e);
        }
    }

    public void update() {
        try {
            String sql = "select * from gs_game where id = 2100";
            GsGame gsGame = jdbcTemplate.queryForObject(sql, GsGame.class);
            log.info("=============before update=============={}", JSON.toJSONString(gsGame));

            sql = "update gs_game set name = '我被修改了', download_num = 111 where id = 2100";
            jdbcTemplate.execute(sql);
            log.info("=============update ok==============");

            sql = "select * from gs_game where id = 2100";
            gsGame = jdbcTemplate.queryForObject(sql, GsGame.class);
            log.info("=============after update=============={}", JSON.toJSONString(gsGame));
        } catch (Exception e) {
            log.error("update error:", e);
        }
    }
}
