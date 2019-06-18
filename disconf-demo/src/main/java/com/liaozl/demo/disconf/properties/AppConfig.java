package com.liaozl.demo.disconf.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/18 14:44
 */
@Data
@Configuration
public class AppConfig {

    @Value("${redis_host}")
    private String redis_host;

    @Value("${redis_port}")
    private Integer redis_port;

    @Value("${redis_database}")
    private Integer redis_database;

    @Value("${redis_password}")
    private String redis_password;

}
