package com.liaozl.demo.disconf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath:spring-disconf.xml"})
@EnableConfigurationProperties
@SpringBootApplication
public class DisconfDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisconfDemoApplication.class, args);
    }

}
