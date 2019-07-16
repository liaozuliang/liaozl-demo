package com.liaozl.demo.neo4j.neo4j.node;

import com.liaozl.demo.neo4j.neo4j.relationship.Relations;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Properties;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 11:02
 */
@Data
@NodeEntity
public class Student extends BaseNode {

    @Property
    private String name;

    @Property
    private Byte sex;

    @Property
    private Integer age;

    @Property
    private Double height;

    @Relationship(type = Relations.STUDY_IN, direction = Relationship.OUTGOING)
    private List<School> studyInSchoolList;

    @Relationship(type = Relations.IS_CHILD_OF, direction = Relationship.OUTGOING)
    private List<Parent> parentList;
}
