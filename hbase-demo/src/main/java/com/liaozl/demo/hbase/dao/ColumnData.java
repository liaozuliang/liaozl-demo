package com.liaozl.demo.hbase.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/20 17:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnData {

    private String familyName;
    private String columnName;
    private String value;
}
