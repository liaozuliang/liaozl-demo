package com.liaozl.demo.oauth2sso.server.service;

import com.liaozl.demo.oauth2sso.server.entity.Role;
import com.liaozl.demo.oauth2sso.server.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/12/3 12:06
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Map<String, UserInfo> USERNAME_USER_MAP = new HashMap<>();

    static {
        Role roleHigh = new Role(1L, "ROLE_HIGH");
        Role roleMid = new Role(2L, "ROLE_MID");
        Role roleLow = new Role(3L, "ROLE_LOW");

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setUserName("admin");
        userInfo.setPassword("admin123");
        userInfo.setSex("男");
        userInfo.setCity("北京");
        userInfo.setRoleList(Arrays.asList(new Role[]{roleHigh, roleMid, roleLow}));

        USERNAME_USER_MAP.put(userInfo.getUserName(), userInfo);

        userInfo = new UserInfo();
        userInfo.setUserId(2L);
        userInfo.setUserName("test");
        userInfo.setPassword("test123");
        userInfo.setSex("女");
        userInfo.setCity("深圳");
        userInfo.setRoleList(Arrays.asList(new Role[]{roleLow}));

        USERNAME_USER_MAP.put(userInfo.getUserName(), userInfo);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        /**
         * 这里实际情况应该是根据参数s查询数据库用户数据
         */
        UserInfo userInfo = USERNAME_USER_MAP.get(userName);
        if (userInfo != null) {
            return new User(userInfo.getUserName(), bCryptPasswordEncoder.encode(userInfo.getPassword()), userInfo.getRoleList());
        }

        return null;
    }
}
