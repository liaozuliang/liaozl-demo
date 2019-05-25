package com.liaozl.demo.kafka.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Object message) {
        kafkaTemplate.send(topic, JSON.toJSONString(message));
        log.info("message send success[topic:{}, message:{}]", topic, JSON.toJSONString(message));
    }

}
