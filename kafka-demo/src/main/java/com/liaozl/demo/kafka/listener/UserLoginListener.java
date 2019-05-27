package com.liaozl.demo.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.kafka.constants.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserLoginListener {

    /**
     * 也可以不指定groupId，则使用配置文件中的配置
     * @param record
     */
    @KafkaListener(topics = {KafkaTopics.USER_LOGIN, KafkaTopics.VIEW_NEWS}, groupId = "myGroupId")
    public void receive(ConsumerRecord<?, ?> record) {
        if (record != null) {
            log.info("UserLoginListener 收到消息, messageKey:{}, message:{}, record:{}", record.key(), record.value().toString(), JSON.toJSONString(record));
        }
    }

}
