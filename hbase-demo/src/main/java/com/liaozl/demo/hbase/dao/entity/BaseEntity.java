package com.liaozl.demo.hbase.dao.entity;

import lombok.Data;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/21 10:45
 */
@Data
public abstract class BaseEntity {

    private String rowKey;
}
