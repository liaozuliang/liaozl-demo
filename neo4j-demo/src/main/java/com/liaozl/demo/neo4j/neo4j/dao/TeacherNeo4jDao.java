package com.liaozl.demo.neo4j.neo4j.dao;

import com.liaozl.demo.neo4j.neo4j.node.Teacher;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 12:15
 */
public interface TeacherNeo4jDao extends Neo4jRepository<Teacher, Long> {
}
