package com.liaozl.demo.storm.topology;

import com.liaozl.demo.storm.bolt.CountBolt;
import com.liaozl.demo.storm.bolt.CountBolt2;
import com.liaozl.demo.storm.bolt.GetUserNameBolt;
import com.liaozl.demo.storm.spout.UserViewPageSpout;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 11:07
 */
@Slf4j
@Service
public class UserViewPageCoutTopology {

    @Autowired
    private UserViewPageSpout userViewPageSpout;

    @Autowired
    private GetUserNameBolt getUserNameBolt;

    @Autowired
    private CountBolt countBolt;

    @Autowired
    private CountBolt2 countBolt2;


    public void submit(String[] args) {
        // 提交storm任务
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("userViewPageSpout", userViewPageSpout);
        builder.setBolt("getUserNameBolt", getUserNameBolt, 1).shuffleGrouping("userViewPageSpout");
        builder.setBolt("countBolt", countBolt, 1).shuffleGrouping("getUserNameBolt");
        builder.setBolt("countBolt2", countBolt2, 2).setNumTasks(2).shuffleGrouping("getUserNameBolt");

        Config conf = new Config();
        conf.put("appName", "这是一个简单storm示例");
        conf.setNumAckers(2);
        conf.setNumWorkers(2);

        try {
            if (System.getProperties().get("os.name").toString().indexOf("Windows") != -1) {
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("UserViewPageCoutTopology", conf, builder.createTopology());

                Thread.sleep(10 * 60 * 1000);
                cluster.shutdown();
            } else {
                StormSubmitter.submitTopologyWithProgressBar("UserViewPageCoutTopology", conf, builder.createTopology());
            }
        } catch (Exception e) {
            log.error("UserViewPageCoutTopology 出错：", e);
        }
    }
}
