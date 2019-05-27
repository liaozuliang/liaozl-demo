package com.liaozl.demo.kafka;

import com.liaozl.demo.kafka.constants.KafkaTopics;
import com.liaozl.demo.kafka.dto.News;
import com.liaozl.demo.kafka.service.KafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaDemoApplicationTests {

    @Autowired
    private KafkaSender kafkaSender;

    @Test
    public void testSendMessage() {
        int i = 0;
        while (i < 10) {
            try {
                kafkaSender.send(KafkaTopics.USER_LOGIN, "我登陆了, i=" + i);

                Thread.sleep(1000);

                News news = new News();
                news.setId(i);
                news.setTitle("标题" + i);
                news.setContent("内容" + i);
                news.setCreateTime(new Date());

                kafkaSender.send(KafkaTopics.VIEW_NEWS, news);
                //kafkaSender.send(KafkaTopics.VIEW_NEWS, "mews_" + news.getId(), news);

                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            i++;
        }
    }

}
