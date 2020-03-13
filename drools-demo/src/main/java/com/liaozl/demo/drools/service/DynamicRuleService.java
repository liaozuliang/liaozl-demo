package com.liaozl.demo.drools.service;

import com.liaozl.demo.drools.entity.DynamicRuleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2020/3/13 12:13
 */
@Slf4j
@Service
public class DynamicRuleService {

    public String doRule1Biz(DynamicRuleDto dto) {
        String str = "(name=" + dto.getName() + ", age=" + dto.getAge() + ") 执行规则1业务";
        return str;
    }

    public String doRule2Biz(DynamicRuleDto dto) {
        String str = "(name=" + dto.getName() + ", age=" + dto.getAge() + ") 执行规则2业务";
        return str;
    }

    public String doRule3Biz(DynamicRuleDto dto) {
        String str = "(name=" + dto.getName() + ", age=" + dto.getAge() + ") 执行规则3业务";
        return str;
    }

    public String doRule4Biz(DynamicRuleDto dto) {
        String str = "(name=" + dto.getName() + ", age=" + dto.getAge() + ") 执行规则4业务";
        return str;
    }
}
