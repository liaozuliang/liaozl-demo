package com.liaozl.demo.storm.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 17:11
 */
@Data
@ConfigurationProperties(prefix = "kafka")
@Configuration
public class KafkaProperties {

    private String servers;

    private String topicName;

    private String topicName2;

    private boolean autoCommit;

    private String groupId;

}
