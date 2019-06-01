package com.liaozl.demo.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.kafka.constants.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MultiPartitionsListener2 {

    @KafkaListener(id = "id0", topicPartitions = {@TopicPartition(topic = KafkaTopics.MULTI_PARTITIONS_TOPIC, partitions = {"0"})})
    public void receive1(ConsumerRecord<?, ?> record) {
        if (record != null) {
            log.info("MultiPartitionsListener2_partition0 收到消息, topic:{}, partition:{} messageKey:{}, message:{}, record:{}", record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
        }
    }

    @KafkaListener(id = "id1", topicPartitions = {@TopicPartition(topic = KafkaTopics.MULTI_PARTITIONS_TOPIC, partitions = {"1"})})
    public void receive2(ConsumerRecord<?, ?> record) {
        if (record != null) {
            log.info("MultiPartitionsListener2_partition1 收到消息, topic:{}, partition:{} messageKey:{}, message:{}, record:{}", record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
        }
    }

    @KafkaListener(id = "id2", topicPartitions = {@TopicPartition(topic = KafkaTopics.MULTI_PARTITIONS_TOPIC, partitions = {"2"})})
    public void receive3(ConsumerRecord<?, ?> record) {
        if (record != null) {
            log.info("MultiPartitionsListener2_partition2 收到消息, topic:{}, partition:{} messageKey:{}, message:{}, record:{}", record.topic(), record.partition(), record.key(), record.value().toString(), JSON.toJSONString(record));
        }
    }

}
