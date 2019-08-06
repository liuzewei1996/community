package com.liu.community.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @ProjectName: community
 * @Package: com.liu.community.entity
 * @ClassName: discussPort
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/17 16:55
 * @Version: 1.0
 */
@Document(indexName = "discusspost",type = "_doc",shards = 5,replicas = 3)
//===============添加es的映射配置注解;名称、类型、分片数、副本
public class DiscussPost {
    @Id//====
    private int id;

    @Field(type = FieldType.Integer)//====
    private int userId;

    //======主要需要查找的内容
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    //=====analyzer存储时候的解析器(分词器),应该分为尽可能多的词，使搜索范围大。
    //======= searchAnalyzer搜索时候的解析器，拆分粗一点就可以
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    @Field(type = FieldType.Integer)//====
    private int type;

    @Field(type = FieldType.Integer)//====
    private int status;

    @Field(type = FieldType.Date)//====
    private Date createTime;

    @Field(type = FieldType.Integer)//====
    private int commentCount;

    @Field(type = FieldType.Double)//====
    private double score;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public double getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "DiscussPost{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", commentCount=" + commentCount +
                ", score=" + score +
                '}';
    }
}
