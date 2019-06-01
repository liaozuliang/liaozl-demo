package com.liaozl.demo.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.kafka.constants.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ViewNewsListener {

    @KafkaListener(topics = {KafkaTopics.VIEW_NEWS})
    public void receive0(ConsumerRecord<?, ?> record) {
        if (record != null) {
            record.topic();
            log.info("ViewNewsListener 收到消息, messageKey:{}, message:{}, record:{}", record.key(), record.value().toString(), JSON.toJSONString(record));
        }
    }

}