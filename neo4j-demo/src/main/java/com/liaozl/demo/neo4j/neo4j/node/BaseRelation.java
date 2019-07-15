package com.liaozl.demo.neo4j.neo4j.node;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 10:55
 */
@Getter
@Setter
public abstract class BaseRelation {

    @Id
    @GeneratedValue
    private Long id;
}
