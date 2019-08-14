package com.liaozl.demo.hadoop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HadoopDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HadoopDemoApplication.class, args);

        HdfsService hdfsService = context.getBean(HdfsService.class);
        boolean ret = hdfsService.upload(args[0], args[1]);
        System.out.println("=====upload====" + ret);
    }

}
