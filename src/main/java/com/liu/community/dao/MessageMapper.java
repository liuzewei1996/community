package com.liu.community.dao;

import com.liu.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    // 查询当前用户的会话列表,针对每个会话只返回一条最新的私信.
    List<Message> selectConversations(int userId, int offset, int limit);

    // 查询当前用户的会话数量.
    int selectConversationCount(int userId);

    // 查询某个会话所包含的私信列表.
    List<Message> selectLetters(String conversationId, int offset, int limit);

    // 查询某个会话所包含的私信数量.
    int selectLetterCount(String conversationId);

    // 查询未读私信的数量
    int selectLetterUnreadCount(int userId, String conversationId);

    //新增消息
    int insertMessage(Message message);

    //修改消息的状态
    int updateStatus(List<Integer> ids, int status);

    //一个用户，多个会话；一个会话是两个人之间的会话，一个会话，多条消息；
    // 查询本用户的会话列表：包含对方的user信息，与用户会话的最后一条消息；

}
