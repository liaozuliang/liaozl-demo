package com.liaozl.demo.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.kafka.constants.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 手动确认方式
 */
@Slf4j
@Service
public class MultiPartitionsListener3 {

    /**
     * 不指定分区，也可以收到所有分区的消息，不指定groupId用的是配置文件里的配置(spring.kafka.consumer.group-id)
     * @param consumerRecordList
     * @param ack
     */
    @KafkaListener(topics = KafkaTopics.MULTI_PARTITIONS_TOPIC)
    public void receive(List<ConsumerRecord> consumerRecordList, Acknowledgment ack) {
        try {
            if (consumerRecordList != null && consumerRecordList.size() > 0) {
                consumerRecordList.forEach(record -> {
                    log.info("MultiPartitionsListener3-ThreadId：{} 收到消息, topic:{}, partition:{}, messageKey:{}, message:{}, record:{}", Thread.currentThread().getId(), record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
                });
            }
        } catch (Exception e) {
            log.error("消息消费出错：", e);
        } finally {
            ack.acknowledge();
        }
    }
}
