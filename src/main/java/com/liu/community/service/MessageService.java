package com.liu.community.service;

import com.liu.community.dao.MessageMapper;
import com.liu.community.entity.Message;
import com.liu.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    public int addMessage(Message message){
        message.setContent(sensitiveFilter.filter(HtmlUtils.htmlEscape(message.getContent())));
        return messageMapper.insertMessage(message);
    }

    public int readMessage(List<Integer> ids){//对id集合：更新读的状态
        return messageMapper.updateStatus(ids, 1);//0为未读，1为已读，2为无权限
    }

}
