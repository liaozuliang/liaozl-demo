package com.liaozl.demo.neo4j.neo4j.node;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 10:55
 */
@Data
@NodeEntity
public class School extends BaseNode {

    @Property
    private String name;

    @Property
    private String address;

    @Property
    private String createTime;
}
