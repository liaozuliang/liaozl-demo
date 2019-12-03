package com.liaozl.demo.oauth2sso.server.controller;

import com.liaozl.demo.oauth2sso.server.entity.UserInfo;
import com.liaozl.demo.oauth2sso.server.service.MyUserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/12/3 15:52
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/userInfo")
    public UserInfo getUserInfo(@RequestParam String userName) {
        return MyUserDetailsService.USERNAME_USER_MAP.get(userName);
    }
}
