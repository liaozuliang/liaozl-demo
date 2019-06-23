package com.liaozl.demo.elasticsearch.es.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liaozl.demo.elasticsearch.dto.Channel;
import lombok.Data;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/22 14:50
 */
@Data
@Document(indexName = "liaozl", type = "liaozl_rec_item", shards = 5, replicas = 1)
public class RecItem extends EsBaseEntity {

    @Id
    private String id;

    // Keyword类型只能精确查询
    @Field(type = FieldType.Keyword)
    private String itemId;

    @Field(type = FieldType.Long)
    private Long newsId;

    // Text类型支持全文检索
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text)
    private String keywords;

    @Field(type = FieldType.Keyword)
    private List<String> kewordList;

    @Field(type = FieldType.Object)
    private List<Channel> channelList;

    @Field(type = FieldType.Integer)
    private Integer readCount;

    @Field(type = FieldType.Boolean)
    private Boolean isViewed;

    @Field(type = FieldType.Keyword, index = false, store = false)
    private String url;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date, format= DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
