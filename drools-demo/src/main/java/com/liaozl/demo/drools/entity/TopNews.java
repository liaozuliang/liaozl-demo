package com.liaozl.demo.drools.entity;

import com.liaozl.demo.drools.enums.NewsTypeEnum;
import com.liaozl.demo.drools.enums.SortTypeEnum;
import lombok.Data;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/5/20 12:28
 */
@Data
public class TopNews {

    private NewsTypeEnum newsType = NewsTypeEnum.TOP;

    private SortTypeEnum sortType = SortTypeEnum.DESC;

    private int count;

    private int score;
}
