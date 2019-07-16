package com.liaozl.demo.neo4j.neo4j.node;

import com.liaozl.demo.neo4j.neo4j.relationship.Relations;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 11:19
 */
@Data
@NodeEntity
public class Teacher extends BaseNode {

    @Property
    private String name;

    private Byte sex;

    @Relationship(type = Relations.GRADUATE_FROM, direction = Relationship.OUTGOING)
    private List<School> graduateFromSchoolList;

    @Relationship(type = Relations.IS_STUDENT_OF, direction = Relationship.INCOMING)
    private List<Student> studentList;
}
