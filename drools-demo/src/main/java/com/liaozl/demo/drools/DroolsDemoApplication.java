package com.liaozl.demo.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DroolsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroolsDemoApplication.class, args);
    }

}
