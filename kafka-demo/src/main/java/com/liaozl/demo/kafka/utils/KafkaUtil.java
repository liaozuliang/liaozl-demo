package com.liaozl.demo.kafka.utils;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.kafka.dto.KafkaTopic;
import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/5/27 10:46
 */
public class KafkaUtil {

    public static void main(String[] args) {
        String zkConnect = "172.10.4.133:2181";
        String topicName = "short_recommend_calc";
        if (!existsTopic(zkConnect, topicName)) {
            KafkaTopic topic = new KafkaTopic();
            topic.setTopicName(topicName);
            topic.setPartition(1);
            topic.setReplication(2);
            topic.setDesc("短期资讯推荐计算");
            createTopic(zkConnect, topic);
            System.out.println(existsTopic(zkConnect, topicName));
        } else {
            Properties props = getTopicProperties(zkConnect, topicName);
            System.out.println("====props:" + JSON.toJSONString(props));
        }
    }

    public static boolean existsTopic(String zkConnect, String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(zkConnect, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        boolean exist = AdminUtils.topicExists(zkUtils, topicName);
        zkUtils.close();
        return exist;
    }

    public static void createTopic(String zkConnect, KafkaTopic topic) {
        ZkUtils zkUtils = ZkUtils.apply(zkConnect, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        AdminUtils.createTopic(zkUtils, topic.getTopicName(), topic.getPartition(),
                topic.getReplication(), new Properties(), new RackAwareMode.Enforced$());
        zkUtils.close();
    }

    public static void deleteTopic(String zkConnect, KafkaTopic topic) {
        ZkUtils zkUtils = ZkUtils.apply(zkConnect, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        AdminUtils.deleteTopic(zkUtils, topic.getTopicName());
        zkUtils.close();
    }

    public static Properties getTopicProperties(String zkConnect, String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(zkConnect, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topicName);
        zkUtils.close();
        return props;
    }

    public static void changeTopic(String zkConnect, String topicName, Map<String, Object> topicProps) {
        ZkUtils zkUtils = ZkUtils.apply(zkConnect, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topicName);
        if (topicProps != null) {
            for (String key : topicProps.keySet()) {
                props.put(key, topicProps.get(key));
            }
        }

        AdminUtils.changeTopicConfig(zkUtils, topicName, props);

        zkUtils.close();
    }

    public static void changeTopicPartition(String zkConnect, String topicName, int paritiionNum) {
        ZkUtils zkUtils = ZkUtils.apply(zkConnect, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        AdminUtils.addPartitions$default$5();
        zkUtils.close();
    }

}
