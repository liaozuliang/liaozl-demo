package com.liaozl.demo.neo4j.neo4j.dao;

import com.liaozl.demo.neo4j.neo4j.node.School;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 12:15
 */
public interface SchoolNeo4jDao extends Neo4jRepository<School, Long> {

}
