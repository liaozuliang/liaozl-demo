package com.liaozl.demo.neo4j.neo4j.relationship;

import com.liaozl.demo.neo4j.neo4j.node.BaseNode;
import com.liaozl.demo.neo4j.neo4j.node.BaseRelation;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 12:19
 */
@Data
@RelationshipEntity(type = Relations.IS_FRIEND_OF)
public class FriendRelation<S extends BaseNode, E extends BaseNode> extends BaseRelation {

    @StartNode
    private S startNode;

    @EndNode
    private E endNode;

    @Property
    private String remark;

    @Property
    private String time;
}
