package com.liaozl.demo.neo4j.neo4j.dao;

import com.liaozl.demo.neo4j.neo4j.node.BaseRelation;
import com.liaozl.demo.neo4j.neo4j.relationship.Relations;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 16:42
 */
public interface RelationDao extends Neo4jRepository<BaseRelation, Long> {

    @Query("MATCH p=()-[r:"+ Relations.IS_FRIEND_OF +"]->() RETURN p")
    List<BaseRelation> findFriendRelation();
}
