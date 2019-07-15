package com.liaozl.demo.neo4j.neo4j.relationship;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/7/15 10:46
 */
public interface Relations {

    // A是B的朋友
    String IS_FRIEND_OF = "IS_FRIEND_OF";

    // A是B的老师
    String IS_TEACHER_OF = "IS_TEACHER_OF";

    // 毕业于
    String GRADUATE_FROM = "GRADUATE_FROM";

    // 在哪里学习
    String STUDY_IN = "STUDY_IN";

    // A是B的孩子
    String IS_CHILD_OF = "IS_CHILD_OF";
}
