package com.liaozl.demo.drools.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/5/20 14:45
 */
@Getter
@AllArgsConstructor
public enum SortTypeEnum {

    ASC((byte) 1, "升序"),
    DESC((byte) 2, "降序"),
    ;

    private Byte type;
    private String desc;
}
