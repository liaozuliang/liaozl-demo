package com.liaozl.demo.storm.bolt;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.storm.dto.UserViewPageMsg;
import com.liaozl.demo.storm.service.CountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 15:39
 */
@Slf4j
@Service
public class CountBolt2 extends BaseRichBolt {

    private Map map;
    private TopologyContext topologyContext;
    private OutputCollector outputCollector;

    @Autowired
    private CountService countService;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        log.info("===========CountBolt2 storm config={}", JSON.toJSONString(map));
        this.map = map;
        this.topologyContext = topologyContext;
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            UserViewPageMsg msg = (UserViewPageMsg) tuple.getValueByField(GetUserNameBolt.FIELD_NAME);
            if (msg != null) {
                countService.countByPageId(msg.getPageId());
                countService.countByIp(msg.getIp());
            }

            outputCollector.ack(tuple);
        } catch (Exception e) {
            log.error("===========CountBolt2 处理出错：", e);
            outputCollector.fail(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
