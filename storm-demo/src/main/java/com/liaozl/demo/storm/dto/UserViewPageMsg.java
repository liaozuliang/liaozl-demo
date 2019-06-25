package com.liaozl.demo.storm.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 10:25
 */
@Data
public class UserViewPageMsg implements Serializable {

    private int userId;

    private String userName;

    private int pageId;

    private String ip;
}
