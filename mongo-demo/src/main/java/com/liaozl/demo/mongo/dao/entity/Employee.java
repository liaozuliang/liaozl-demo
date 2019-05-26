package com.liaozl.demo.mongo.dao.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "t_employee")
public class Employee {

    /**
     * 此处没有用@Id也映射到了数据库_id字段
     */
    private Integer id;

    @Indexed
    private String name;

    private Byte sex;

    private int age;

    @Field("company_id")
    private Integer companyId;

    @Field("create_time")
    private Date createTime;
}
