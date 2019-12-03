package com.liaozl.demo.oauth2sso.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/12/3 12:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    private Long roleId;

    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
