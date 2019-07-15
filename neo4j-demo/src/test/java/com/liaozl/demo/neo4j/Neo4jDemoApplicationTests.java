package com.liaozl.demo.neo4j;

import com.liaozl.demo.neo4j.neo4j.dao.ParentNeo4jDao;
import com.liaozl.demo.neo4j.neo4j.dao.RelationDao;
import com.liaozl.demo.neo4j.neo4j.dao.SchoolNeo4jDao;
import com.liaozl.demo.neo4j.neo4j.dao.StudentNeo4jDao;
import com.liaozl.demo.neo4j.neo4j.dao.TeacherNeo4jDao;
import com.liaozl.demo.neo4j.neo4j.node.Parent;
import com.liaozl.demo.neo4j.neo4j.node.School;
import com.liaozl.demo.neo4j.neo4j.node.Student;
import com.liaozl.demo.neo4j.neo4j.node.Teacher;
import com.liaozl.demo.neo4j.neo4j.relationship.FriendRelation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4jDemoApplicationTests {

    @Autowired
    private ParentNeo4jDao parentNeo4jDao;

    @Autowired
    private SchoolNeo4jDao schoolNeo4jDao;

    @Autowired
    private StudentNeo4jDao studentNeo4jDao;

    @Autowired
    private TeacherNeo4jDao teacherNeo4jDao;

    @Autowired
    private RelationDao relationDao;

    @Test
    public void initData() {
        //====================School
        School s1 = new School();
        s1.setName("北京大学");
        s1.setAddress("北京");
        s1.setCreateTime("1885-07-01");
        s1 = schoolNeo4jDao.save(s1);

        School s2 = new School();
        s2.setName("深圳大学");
        s2.setAddress("深圳");
        s2.setCreateTime("1915-09-01");
        s2 = schoolNeo4jDao.save(s2);

        //====================Teacher
        Teacher t1 = new Teacher();
        t1.setName("张老师");
        t1.setSex(Byte.valueOf("0"));
        t1.setGraduateFromSchoolList(Arrays.asList(s1));
        t1 = teacherNeo4jDao.save(t1);

        Teacher t2 = new Teacher();
        t2.setName("王老师");
        t2.setSex(Byte.valueOf("1"));
        t2.setGraduateFromSchoolList(Arrays.asList(s2));
        t2 = teacherNeo4jDao.save(t2);

        Teacher t3 = new Teacher();
        t3.setName("李老师");
        t3.setSex(Byte.valueOf("0"));
        t3.setGraduateFromSchoolList(Arrays.asList(s1, s2));
        t3 = teacherNeo4jDao.save(t3);

        //====================Parent
        Parent p1 = new Parent();
        p1.setName("家长A");
        p1.setGraduateFromSchoolList(Arrays.asList(s1));
        p1 = parentNeo4jDao.save(p1);

        Parent p2 = new Parent();
        p2.setName("家长B");
        p2.setGraduateFromSchoolList(Arrays.asList(s2));
        p2 = parentNeo4jDao.save(p2);

        Parent p3 = new Parent();
        p3.setName("家长C");
        p3.setGraduateFromSchoolList(Arrays.asList(s1, s2));
        p3 = parentNeo4jDao.save(p3);

        //====================Student
        Student stu1 = new Student();
        stu1.setName("学生1");
        stu1.setSex(Byte.valueOf("0"));
        stu1.setAge(19);
        stu1.setHeight(176.8);
        stu1.setStudyInSchoolList(Arrays.asList(s1));
        stu1 = studentNeo4jDao.save(stu1);

        Student stu2 = new Student();
        stu2.setName("学生2");
        stu2.setSex(Byte.valueOf("1"));
        stu2.setAge(29);
        stu2.setHeight(186.3);
        stu2.setStudyInSchoolList(Arrays.asList(s1, s2));
        stu2 = studentNeo4jDao.save(stu2);

        Student stu3 = new Student();
        stu3.setName("学生3");
        stu3.setSex(Byte.valueOf("1"));
        stu3.setAge(23);
        stu3.setHeight(160d);
        stu3.setStudyInSchoolList(Arrays.asList(s2));
        stu3 = studentNeo4jDao.save(stu3);

        Student stu4 = new Student();
        stu4.setName("学生4");
        stu4.setSex(Byte.valueOf("0"));
        stu4.setAge(36);
        stu4.setHeight(196.2);
        stu4 = studentNeo4jDao.save(stu4);

        //====================Teacher-student
        t1.setStudentList(Arrays.asList(stu1, stu3));
        t1 = teacherNeo4jDao.save(t1);

        t2.setStudentList(Arrays.asList(stu2, stu4));
        t2 = teacherNeo4jDao.save(t2);

        t3.setStudentList(Arrays.asList(stu1, stu2, stu3, stu4));
        t3 = teacherNeo4jDao.save(t3);

        //====================Parent-friend
        FriendRelation<Parent, Parent> fr1 = new FriendRelation<>();
        fr1.setStartNode(p1);
        fr1.setEndNode(p2);
        fr1.setRemark("家长和家长成为了朋友");
        fr1.setTime("2019-06-01");
        relationDao.save(fr1);

        FriendRelation<Parent, Parent> fr2 = new FriendRelation<>();
        fr2.setStartNode(p2);
        fr2.setEndNode(p1);
        fr2.setRemark("家长和家长成为了朋友");
        fr2.setTime("2019-06-01");
        relationDao.save(fr2);

        FriendRelation<Parent, Parent> fr3 = new FriendRelation<>();
        fr3.setStartNode(p1);
        fr3.setEndNode(p3);
        fr3.setRemark("家长和家长成为了朋友");
        fr3.setTime("2018-06-01");
        relationDao.save(fr3);

        FriendRelation<Parent, Parent> fr4 = new FriendRelation<>();
        fr4.setStartNode(p2);
        fr4.setEndNode(p3);
        fr4.setRemark("家长和家长成为了朋友");
        fr4.setTime("2018-06-01");
        relationDao.save(fr4);

        FriendRelation<Parent, Teacher> fr5 = new FriendRelation<>();
        fr5.setStartNode(p3);
        fr5.setEndNode(t1);
        fr5.setRemark("家长和老师成为了朋友");
        fr5.setTime("2018-06-01");
        relationDao.save(fr5);

        //====================Student-parent
        stu1.setParentList(Arrays.asList(p1));
        stu1 = studentNeo4jDao.save(stu1);

        stu2.setParentList(Arrays.asList(p1, p2));
        stu2 = studentNeo4jDao.save(stu2);

        stu3.setParentList(Arrays.asList(p2, p3));
        stu3 = studentNeo4jDao.save(stu3);

        stu4.setParentList(Arrays.asList(p3));
        stu4 = studentNeo4jDao.save(stu4);
    }

    @Test
    public void testQuery() {
    }

}
