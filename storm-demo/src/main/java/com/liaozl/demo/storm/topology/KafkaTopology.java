package com.liaozl.demo.storm.topology;

import com.liaozl.demo.storm.bolt.KafkaMsgHandleBolt;
import com.liaozl.demo.storm.properties.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.spout.ByTopicRecordTranslator;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 16:22
 */
@Slf4j
@Service
public class KafkaTopology {

    public static final String MSG_BODY_FIELD_NAME = "msgBody";
    public static final String TOPIC_NAME_FIELD_NAME = "topicName";

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaMsgHandleBolt kafkaMsgHandleBolt;

    public void submit(String[] args) {
        // 提交storm任务
        TopologyBuilder builder = new TopologyBuilder();

        // kafka记录转换为tuple
        ByTopicRecordTranslator<String, String> byTopicRecordTranslator = new ByTopicRecordTranslator<String, String>(
                (record) -> new Values(record.value(), record.topic()), new Fields(MSG_BODY_FIELD_NAME, TOPIC_NAME_FIELD_NAME)
        );

        //设置消费topic
        byTopicRecordTranslator.forTopic("xxx", (record) -> new Values(record.value(), record.topic()), new Fields(MSG_BODY_FIELD_NAME, TOPIC_NAME_FIELD_NAME));

        KafkaSpoutConfig<String, String> kafkaSpoutConfig = KafkaSpoutConfig
                .builder(kafkaProperties.getServers(), kafkaProperties.getTopicName(), kafkaProperties.getTopicName2())
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId())
                .setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST)
                .setRecordTranslator(byTopicRecordTranslator)
                .build();

        builder.setSpout("kafkaMsgSpout", new KafkaSpout<>(kafkaSpoutConfig));

        builder.setBolt("kafkaMsgHandleBolt", kafkaMsgHandleBolt, 2).setNumTasks(2).shuffleGrouping("kafkaMsgSpout");

        Config conf = new Config();
        conf.put("appName", "这是一个处理kafka消息的storm示例");

        try {
            if (System.getProperties().get("os.name").toString().indexOf("Windows") != -1) {
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("KafkaTopology", conf, builder.createTopology());

                Thread.sleep(10 * 60 * 1000);
                cluster.shutdown();
            } else {
                StormSubmitter.submitTopologyWithProgressBar("KafkaTopology", conf, builder.createTopology());
            }
        } catch (Exception e) {
            log.error("KafkaTopology 出错：", e);
        }
    }
}
