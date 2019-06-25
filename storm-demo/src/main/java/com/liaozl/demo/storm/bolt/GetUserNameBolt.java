package com.liaozl.demo.storm.bolt;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.storm.dto.UserViewPageMsg;
import com.liaozl.demo.storm.spout.UserViewPageSpout;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 10:53
 */
@Slf4j
@Service
public class GetUserNameBolt extends BaseRichBolt {

    private Map map;
    private TopologyContext topologyContext;
    private OutputCollector outputCollector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        log.info("===========GetUserNameBolt storm config={}", JSON.toJSONString(map));
        this.map = map;
        this.topologyContext = topologyContext;
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            UserViewPageMsg msg = (UserViewPageMsg) tuple.getValueByField(UserViewPageSpout.FIELD_NAME);
            if (msg != null) {
                log.info("=======GetUserNameBolt，messageId={} 正常查询用户名称，msg={}", tuple.getMessageId().toString(), JSON.toJSONString(msg));
                msg.setUserName("张三" + msg.getUserId());
                outputCollector.emit(new Values(msg));
            }
            outputCollector.ack(tuple);
        } catch (Exception e) {
            log.error("=======GetUserNameBolt 处理出错：", e);
            outputCollector.fail(tuple);
        }

    }

    public static final String FIELD_NAME = "userViewPageMsg_with_userName";

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(FIELD_NAME));
    }
}
