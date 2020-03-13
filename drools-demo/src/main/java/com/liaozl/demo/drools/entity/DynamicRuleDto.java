package com.liaozl.demo.drools.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2020/3/13 12:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicRuleDto {

    private String name;

    private int age;

    private String callRuleResult;
}
