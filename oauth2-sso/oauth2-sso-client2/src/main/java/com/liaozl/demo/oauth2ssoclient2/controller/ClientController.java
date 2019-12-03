package com.liaozl.demo.oauth2ssoclient2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/12/3 16:06
 */
@RestController
public class ClientController {

    @GetMapping("/high")
    @PreAuthorize("hasAuthority('ROLE_HIGH')")
    public String normal( ) {
        return "high permission";
    }

    @GetMapping("/mid")
    @PreAuthorize("hasAuthority('ROLE_MID')")
    public String medium() {
        return "mid permission";
    }

    @GetMapping("/low")
    @PreAuthorize("hasAuthority('ROLE_LOW')")
    public String admin() {
        return "low permission";
    }
}
