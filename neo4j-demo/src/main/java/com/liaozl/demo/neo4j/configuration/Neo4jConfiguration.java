package com.liaozl.demo.neo4j.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/12 10:53
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.liaozl.demo.neo4j.neo4j")
@EnableTransactionManagement
public class Neo4jConfiguration {

}
