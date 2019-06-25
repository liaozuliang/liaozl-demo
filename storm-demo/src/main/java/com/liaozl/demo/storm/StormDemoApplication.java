package com.liaozl.demo.storm;

import com.liaozl.demo.storm.topology.KafkaTopology;
import com.liaozl.demo.storm.topology.UserViewPageCoutTopology;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@EnableConfigurationProperties
@SpringBootApplication
public class StormDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(StormDemoApplication.class, args);

        //startupUserViewPageCoutTopology(applicationContext, args);

        startupKafkaTopology(applicationContext, args);
    }

    private static void startupUserViewPageCoutTopology(ConfigurableApplicationContext applicationContext, String[] args) {
        UserViewPageCoutTopology userViewPageCoutTopology = applicationContext.getBean(UserViewPageCoutTopology.class);
        userViewPageCoutTopology.submit(args);
    }


    private static void startupKafkaTopology(ConfigurableApplicationContext applicationContext, String[] args) {
        KafkaTopology kafkaTopology = applicationContext.getBean(KafkaTopology.class);
        kafkaTopology.submit(args);
    }

}
