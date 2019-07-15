package com.liaozl.demo.neo4j.neo4j.dao;

import com.liaozl.demo.neo4j.neo4j.node.BaseRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 16:42
 */
public interface RelationDao extends Neo4jRepository<BaseRelation, Long> {
}
