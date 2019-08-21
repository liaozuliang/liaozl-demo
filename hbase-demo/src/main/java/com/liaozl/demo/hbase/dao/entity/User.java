package com.liaozl.demo.hbase.dao.entity;

import lombok.Data;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/21 9:23
 */
@Data
public class User extends BaseEntity {

    private String name;
    private String sex;
    private String age;
    private String image;
    private String address;
}
