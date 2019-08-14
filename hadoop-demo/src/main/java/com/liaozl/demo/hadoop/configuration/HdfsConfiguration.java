package com.liaozl.demo.hadoop.configuration;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/14 11:03
 */
@Configuration
public class HdfsConfiguration {

    @Value("${hdfsUri}")
    private String hdfsUri;

    @Value("${hadoopUserName}")
    private String hadoopUserName;

    @Bean
    public FileSystem gettFileSystem() throws Exception {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        return FileSystem.get(new URI(hdfsUri), conf, hadoopUserName);
    }

}
