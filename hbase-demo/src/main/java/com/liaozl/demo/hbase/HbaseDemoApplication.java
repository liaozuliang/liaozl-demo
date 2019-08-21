package com.liaozl.demo.hbase;

import com.liaozl.demo.hbase.service.HBaseDaoTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class HbaseDemoApplication {

    /**
     * 须打包发布到集群环境测试
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HbaseDemoApplication.class, args);

        HBaseDaoTestService hBaseDaoTestService = context.getBean(HBaseDaoTestService.class);

        String opt = args[0];
        if ("testAdd".equals(opt)) {
            hBaseDaoTestService.testAdd();
        } else if ("testQuery".equals(opt)) {
            hBaseDaoTestService.testQuery();
        } else if ("testDelete".equals(opt)) {
            hBaseDaoTestService.testDelete();
        }
    }
}
