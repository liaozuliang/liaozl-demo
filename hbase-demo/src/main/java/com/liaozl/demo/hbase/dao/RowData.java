package com.liaozl.demo.hbase.dao;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/20 17:20
 */
@Data
public class RowData {

    private String rowKey;

    private List<ColumnData> columnDataList;

}
