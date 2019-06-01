package com.liaozl.demo.kafka;

import com.liaozl.demo.kafka.constants.KafkaTopics;
import com.liaozl.demo.kafka.dto.News;
import com.liaozl.demo.kafka.service.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaDemoApplicationTests {

    @Autowired
    private KafkaSender kafkaSender;

    @Test
    public void testListener() {
        int i = 0;
        while (i < 100) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            i++;
        }
    }

    @Test
    public void testSendMessage() {
        int i = 0;
        while (i < 100) {
            try {
                sendMessgae(i);

                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            i++;
        }
    }

    private void sendMessgae(int i) throws InterruptedException {
        kafkaSender.send(KafkaTopics.USER_LOGIN, "我登陆了, i=" + i);

        News news = new News();
        news.setId(i);
        news.setTitle("标题" + i);
        news.setContent("内容" + i);
        news.setCreateTime(new Date());

        kafkaSender.send(KafkaTopics.VIEW_NEWS, news);
        kafkaSender.send(KafkaTopics.VIEW_NEWS, "mews_" + news.getId(), news);


        kafkaSender.send(KafkaTopics.MULTI_PARTITIONS_TOPIC, news);
        kafkaSender.send(KafkaTopics.MULTI_PARTITIONS_TOPIC, "myKey", news);

        if (i % 3 == 0) {
            kafkaSender.send(KafkaTopics.MULTI_PARTITIONS_TOPIC, 0, "myKey0", news);
        }

        if (i % 3 == 1) {
            kafkaSender.send(KafkaTopics.MULTI_PARTITIONS_TOPIC, 1, "myKey1", news);
        }

        if (i % 3 == 2) {
            kafkaSender.send(KafkaTopics.MULTI_PARTITIONS_TOPIC, 2, "myKey2", news);
        }

        if (i % 4 == 2) {
            kafkaSender.send(KafkaTopics.MULTI_PARTITIONS_TOPIC, null, "myKey" + i, news);
        }

        // 根据Key不同发往不同的分区
        ListenableFuture<SendResult<String, String>> future = kafkaSender.send2(KafkaTopics.MULTI_PARTITIONS_TOPIC, null, "myKey" + i, news);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable e) {
                log.error("消息发送失败", e);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("消息发送成功，ProducerRecord:{}, RecordMetadata.partition:{}", result.getProducerRecord().toString(), result.getRecordMetadata().partition());
            }
        });

        ListenableFuture<SendResult<String, String>> future2 = kafkaSender.send2(KafkaTopics.MULTI_PARTITIONS_TOPIC, 1, "=====myKey" + i, news);
        future2.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("消息发送失败");
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("消息发送成功，ProducerRecord:{}, RecordMetadata:{}", result.getProducerRecord().toString(), result.getRecordMetadata().toString());
            }
        });
    }

}
