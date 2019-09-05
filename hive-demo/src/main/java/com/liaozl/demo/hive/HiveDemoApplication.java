package com.liaozl.demo.hive;

import com.liaozl.demo.hive.service.HiveService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HiveDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HiveDemoApplication.class, args);
        HiveService hiveService = context.getBean(HiveService.class);

        String optType = args[0];
        switch (optType) {
            case "testShowDatabases":
                hiveService.showDatabases();
                break;
            case "testCreateTable":
                hiveService.createTable();
                break;
            case "testLoadData":
                hiveService.loadData();
                break;
            case "testInsert":
                hiveService.insert();
                break;
            case "testUpdate":
                hiveService.update();
                break;
            case "testDelete":
                hiveService.delete();
                break;
            case "testQuery":
                hiveService.query();
                break;
            case "testQueryAll":
                hiveService.queryAll();
                break;
            case "testCount":
                hiveService.count();
                break;
        }
    }

}
