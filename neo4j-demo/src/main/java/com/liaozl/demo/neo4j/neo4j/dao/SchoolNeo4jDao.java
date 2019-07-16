package com.liaozl.demo.neo4j.neo4j.dao;

import com.liaozl.demo.neo4j.neo4j.node.School;
import com.liaozl.demo.neo4j.neo4j.node.Student;
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
public interface SchoolNeo4jDao extends Neo4jRepository<School, Long> {

    List<School> findByName(String name);

    List<School> findByNameEndingWith(String name);

    @Query("MATCH (p:Parent)-[r:" + Relations.GRADUATE_FROM + "]->(s:School) WHERE p.name={parentName} RETURN s")
    List<School> findByParent(String parentName);

    @Query("MATCH (t:Teacher)-[r:" + Relations.GRADUATE_FROM + "]->(s:School) WHERE s.name={schoolName} RETURN t")
    List<Teacher> findTeachersBySchoolName(String schoolName);

    @Query("MATCH (stu:Student)-[r:" + Relations.STUDY_IN + "]->(s:School) WHERE s.name={schoolName} RETURN stu")
    List<Student> findStudentsBySchoolName(String schoolName);

    @Query("MATCH (stu:Student)-[r:" + Relations.STUDY_IN + "]->(s:School) WHERE s.name={schoolName} RETURN stu ORDER BY stu.name DESC SKIP 0 LIMIT 100")
    List<Student> findStudentsBySchoolName2(String schoolName);
}