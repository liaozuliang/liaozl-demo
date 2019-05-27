package com.liaozl.demo.kafka.dto;

import lombok.Data;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/5/27 10:45
 */
@Data
public class KafkaTopic {

    private String topicName;       // 名称
    private Integer partition;      // 分区数量
    private Integer replication;    // 副本数量
    private String desc;            // 描述
}
