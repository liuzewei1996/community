package com.liu.community.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: community
 * @Package: com.liu.community.entity
 * @ClassName: Event
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/30 20:30
 * @Version: 1.0
 */
public class Event {
    //事件对信息进行了包装
    //封装：点赞、评论、关注三类情形；
    //topic指哪种情形；userId操作的用户id，entityId对什么实体操作的id，
    // entityType对什么实体操作的类型，entityUserId为该被操作实体的用户id
    private String topic;
    private int userId;
    private int entityType;
    private int entityId;
    private int entityUserId;
    Map<String,Object> data = new HashMap<>();//支持可扩展

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;//即灵活，又方便；在使用的时候可以：setTopic().setUserId().......这样来使用
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;//即灵活，又方便；在使用的时候可以：setTopic().setUserId().......这样来使用

    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;//即灵活，又方便；在使用的时候可以：setTopic().setUserId().......这样来使用

    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;//即灵活，又方便；在使用的时候可以：setTopic().setUserId().......这样来使用

    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;//即灵活，又方便；在使用的时候可以：setTopic().setUserId().......这样来使用
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;//即灵活，又方便；在使用的时候可以：setTopic().setUserId().......这样来使用
    }
}
