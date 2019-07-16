package com.liaozl.demo.neo4j.neo4j.dao;

import com.liaozl.demo.neo4j.neo4j.node.Teacher;
import com.liaozl.demo.neo4j.neo4j.relationship.Relations;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 12:15
 */
public interface TeacherNeo4jDao extends Neo4jRepository<Teacher, Long> {

    @Query("MATCH (t:Teacher) WHERE ID(t) IN {ids} RETURN t")
    List<Teacher> findByIdIn(List<Long> ids);

    @Query("MATCH (s:Student)-[r:" + Relations.IS_STUDENT_OF + "]->(t:Teacher) WHERE s.name={studentName} RETURN t")
    List<Teacher> findByStudent(String studentName);
}