package com.liaozl.demo.disconf.properties;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/18 14:44
 */
@ToString
@Setter
@Configuration
@DisconfFile(filename = "config.properties")
public class AppConfig2 {

    private String redis_host;

    private Integer redis_port;

    private Integer redisMaxIdle;

    @DisconfFileItem(name = "redis_host", associateField = "redis_host")
    public String getRedis_host() {
        return redis_host;
    }

    @DisconfFileItem(name = "redis_port", associateField = "redis_port")
    public Integer getRedis_port() {
        return redis_port;
    }

    // associateField须对应bean的字段名
    @DisconfFileItem(name = "redis.maxIdle", associateField = "redisMaxIdle")
    public Integer getRedisMaxIdle() {
        return redisMaxIdle;
    }
}
