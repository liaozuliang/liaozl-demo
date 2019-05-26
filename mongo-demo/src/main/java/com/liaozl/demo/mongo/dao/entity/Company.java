package com.liaozl.demo.mongo.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "t_company")
public class Company {

    /**
     * 映射数据库中的_id字段
     */
    @Id
    private Integer id;

    private String name;

    private String address;

    private Date createTime;
}
