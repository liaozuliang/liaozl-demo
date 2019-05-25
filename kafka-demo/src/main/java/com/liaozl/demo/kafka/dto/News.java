package com.liaozl.demo.kafka.dto;

import lombok.Data;

import java.util.Date;

@Data
public class News {

    private int id;

    private String title;

    private String content;

    private Date createTime;
}
