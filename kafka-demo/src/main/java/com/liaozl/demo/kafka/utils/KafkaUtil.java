package com.liaozl.demo.kafka.utils;

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

    public static void aa() {
    }

    public static boolean existsTopic(String zkConnect, String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(zkConnect, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        return AdminUtils.topicExists(zkUtils, topicName);
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

}
