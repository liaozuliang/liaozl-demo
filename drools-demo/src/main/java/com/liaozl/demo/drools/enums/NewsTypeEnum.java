package com.liaozl.demo.drools.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/5/20 14:37
 */
@Getter
@AllArgsConstructor
public enum NewsTypeEnum {

    TOP((byte) 1, "推荐"),
    HOT((byte) 2, "热点"),
    ;

    private Byte type;
    private String desc;

}
