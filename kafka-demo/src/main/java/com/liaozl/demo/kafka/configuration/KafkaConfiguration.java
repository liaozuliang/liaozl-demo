package com.liaozl.demo.kafka.configuration;

import com.liaozl.demo.kafka.constants.KafkaTopics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/5/27 10:07
 */
@Configuration
public class KafkaConfiguration {

    @Value("${zookeeper.connect}")
    private String zkConnect;

    @Bean
    public void createTop() {
        List<String> topicNameList = new ArrayList<>();
        topicNameList.add(KafkaTopics.USER_LOGIN);
        topicNameList.add(KafkaTopics.VIEW_NEWS);
    }

}
