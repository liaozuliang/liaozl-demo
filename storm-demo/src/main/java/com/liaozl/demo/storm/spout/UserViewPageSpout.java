package com.liaozl.demo.storm.spout;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.storm.dto.UserViewPageMsg;
import com.liaozl.demo.storm.service.GetMsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 10:23
 */
@Slf4j
@Service
public class UserViewPageSpout extends BaseRichSpout {

    @Autowired
    private GetMsgService getMsgService;

    private Map map;
    private TopologyContext topologyContext;
    private SpoutOutputCollector spoutOutputCollector;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        log.info("===========UserViewPageSpout storm config={}", JSON.toJSONString(map));
        this.map = map;
        this.topologyContext = topologyContext;
        this.spoutOutputCollector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        try {
            UserViewPageMsg msg = getMsgService.getMsg();

            log.info("=======UserViewPageSpout 开始发送数据，data={}", JSON.toJSONString(msg));
            spoutOutputCollector.emit(new Values(msg));

            Thread.sleep(1000);
        } catch (Exception e) {
            log.error("=======UserViewPageSpout 发送数据出错: ", e);
        }
    }

    public static final String FIELD_NAME = "userViewPageMsg_no_userName";

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(FIELD_NAME));
    }

    @Override
    public void close() {
        log.info("===UserViewPageSpout====关闭========");
    }

    @Override
    public void ack(Object msgId) {
        log.info("===UserViewPageSpout=====成功========msgId={}", JSON.toJSONString(msgId));
    }

    @Override
    public void fail(Object msgId) {
        log.info("===UserViewPageSpout=====失败========msgId={}", JSON.toJSONString(msgId));
    }
}
