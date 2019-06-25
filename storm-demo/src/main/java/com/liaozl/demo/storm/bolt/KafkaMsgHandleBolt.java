package com.liaozl.demo.storm.bolt;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.storm.topology.KafkaTopology;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 16:28
 */
@Slf4j
@Service
public class KafkaMsgHandleBolt extends BaseRichBolt {

    private Map map;
    private TopologyContext topologyContext;
    private OutputCollector outputCollector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        log.info("===========KafkaMsgHandleBolt storm config={}", JSON.toJSONString(map));
        this.map = map;
        this.topologyContext = topologyContext;
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            String msgBody = tuple.getStringByField(KafkaTopology.MSG_BODY_FIELD_NAME);
            String topicName = tuple.getStringByField(KafkaTopology.TOPIC_NAME_FIELD_NAME);

            log.info("===========KafkaMsgHandleBolt 开始处理kafka消息，topicName={}, msgBody={}", topicName, msgBody);

            outputCollector.ack(tuple);
        } catch (Exception e) {
            log.error("===========KafkaMsgHandleBolt 处理出错：", e);
            outputCollector.fail(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
