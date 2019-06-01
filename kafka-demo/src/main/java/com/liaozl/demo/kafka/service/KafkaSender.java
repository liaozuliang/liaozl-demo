package com.liaozl.demo.kafka.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Object message) {
        kafkaTemplate.send(topic, JSON.toJSONString(message));
        log.info("message send success[topic:{}, message:{}]", topic, JSON.toJSONString(message));
    }

    public void send(String topic, String messageKey, Object message) {
        kafkaTemplate.send(topic, messageKey, JSON.toJSONString(message));
        log.info("message send success[topic:{}, messageKey:{}, message:{}]", topic, messageKey, JSON.toJSONString(message));
    }

    public void send(String topic, Integer partition, String messageKey, Object message) {
        kafkaTemplate.send(topic, partition, messageKey, JSON.toJSONString(message));
        log.info("message send success[topic:{}, partition:{}, messageKey:{}, message:{}]", topic, partition, messageKey, JSON.toJSONString(message));
    }

    /**
     * partition为null时，会 根据key计算分配分区
     * @param topic
     * @param partition
     * @param messageKey
     * @param message
     * @return
     */
    public ListenableFuture<SendResult<String, String>> send2(String topic, Integer partition, String messageKey, Object message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, partition, messageKey, JSON.toJSONString(message));
        log.info("message send success[topic:{}, partition:{}, messageKey:{}, message:{}]", topic, partition, messageKey, JSON.toJSONString(message));
        return future;
    }

}
