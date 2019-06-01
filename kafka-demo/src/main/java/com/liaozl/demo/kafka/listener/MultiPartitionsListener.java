package com.liaozl.demo.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.kafka.constants.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MultiPartitionsListener {

    /**
     * 不指定分区，也可以收到所有分区的消息，不指定groupId用的是配置文件里的配置(spring.kafka.consumer.group-id)
     *
     * @param record
     */
    @KafkaListener(topics = KafkaTopics.MULTI_PARTITIONS_TOPIC)
    public void receive1(ConsumerRecord<?, ?> record) {
        if (record != null && record.partition() == 0) {
            log.info("=====topic:{}, partition:{}, MultiPartitionsListener 收到消息, messageKey:{}, message:{}, record:{}", record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
        } else if (record != null && record.partition() == 1) {
            log.info("==========topic:{}, partition:{}, MultiPartitionsListener 收到消息, messageKey:{}, message:{}, record:{}", record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
        } else if (record != null && record.partition() == 2) {
            log.info("===============topic:{}, partition:{}, MultiPartitionsListener 收到消息, messageKey:{}, message:{}, record:{}", record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
        } else if (record != null && record.partition() == 3) {
            log.info("***============topic:{}, partition:{}, MultiPartitionsListener 收到消息, messageKey:{}, message:{}, record:{}", record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
        } else {
            log.info("*******topic:{}, partition:{}, MultiPartitionsListener 收到消息, messageKey:{}, message:{}, record:{}", record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
        }
    }
}
