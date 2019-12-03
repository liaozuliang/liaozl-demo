package com.liaozl.demo.oauth2sso.server.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/12/3 12:01
 */
@Data
public class UserInfo {

    private Long userId;

    private String userName;

    private String password;

    private String sex;

    private String city;

    private List<Role> roleList;
}
