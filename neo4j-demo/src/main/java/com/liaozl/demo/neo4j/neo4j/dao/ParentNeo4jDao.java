package com.liaozl.demo.neo4j.neo4j.dao;

import com.liaozl.demo.neo4j.neo4j.node.Parent;
import com.liaozl.demo.neo4j.neo4j.relationship.Relations;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 11:37
 */
public interface ParentNeo4jDao extends Neo4jRepository<Parent, Long> {

    @Query("MATCH (p:Parent)-[r:" + Relations.GRADUATE_FROM + "]->(s:School) WHERE s.name={schoolName} RETURN p")
    List<Parent> findBySchool(String schoolName);

    @Query("MATCH (s:Student)-[r:" + Relations.IS_CHILD_OF + "]->(p:Parent) WHERE s.name={studentName} RETURN p")
    List<Parent> findByStudent(String studentName);

    @Query("MATCH (p:Parent)<-[r:" + Relations.IS_FRIEND_OF + "]->(p2:Parent) WHERE p2.name={parentName} RETURN p " +
           "UNION " +
           "MATCH (t:Teacher)<-[r:" + Relations.IS_FRIEND_OF + "]->(p:Parent) WHERE t.name={teacherName} RETURN p "
    )
    List<Parent> findByFriends(String parentName, String teacherName);
}
