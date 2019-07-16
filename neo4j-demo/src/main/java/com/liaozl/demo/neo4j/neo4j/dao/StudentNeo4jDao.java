package com.liaozl.demo.neo4j.neo4j.dao;

import com.liaozl.demo.neo4j.neo4j.node.Student;
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
public interface StudentNeo4jDao extends Neo4jRepository<Student, Long> {

    List<Student> findByAgeBetween(int minAge, int maxAge);

    @Query("match (s:Student)-[r:" + Relations.IS_STUDENT_OF + "]->(t:Teacher) WHERE t.name={teacherName} return s")
    List<Student> findByTeacher(String teacherName);

    @Query("match (s:Student)-[r:" + Relations.IS_CHILD_OF + "]->(p:Parent) WHERE p.name={parentName} return s,r,p")
    List<Student> findByParent(String parentName);

}