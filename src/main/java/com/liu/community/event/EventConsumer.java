package com.liu.community.event;

import com.alibaba.fastjson.JSONObject;
import com.liu.community.entity.DiscussPost;
import com.liu.community.entity.Event;
import com.liu.community.entity.Message;
import com.liu.community.service.DiscussPostService;
import com.liu.community.service.ElasticsearchService;
import com.liu.community.service.MessageService;
import com.liu.community.util.CommunityConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: community
 * @Package: com.liu.community.event
 * @ClassName: EventConsumer
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/30 20:47
 * @Version: 1.0
 */
@Component
public class EventConsumer implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private ElasticsearchService elasticsearchService;


    //系统通知功能：提示：关注； 评论；点赞；

    //可以一个方法消费一个主题，也可以一个方法消费多个主题；
    //一个主题也可以被多个方法消费
    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_FOLLOW,TOPIC_LIKE})
    public void handleCommentMessage(ConsumerRecord record){
        if(record == null || record.value() == null){
            logger.error("消息为空");
            return;
        }

        //将json格式的数据转化为string再转化为Event对象格式
        Event event = JSONObject.parseObject(record.value().toString(),Event.class);
        if(event == null){
            logger.error("消息格式错误");
            return;
        }

        //此处发送通知：本系统是系统id为1（message表中的发送者id：fromId为1），conversationId
        // 也不用存拼起来的id了，也以用来存储系统通知的主题如：关注、点赞、评论
        //发送站内通知：复用了message表
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());//存主题
        message.setCreateTime(new Date());

        Map<String, Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());
        if(!event.getData().isEmpty()){
            for(Map.Entry<String,Object> entry : event.getData().entrySet()){
                content.put(entry.getKey(), entry.getValue());
            }
        }
        message.setContent(JSONObject.toJSONString(content));
        //具体内容由以上信息拼出：在页面显示的是 ： 用户**评论了你的帖子 ，点击查看；

        messageService.addMessage(message);
    }


    //=======================消费传来的触发消息：根据消息向elasticsearch服务器增加数据==============================
    // 消费发帖事件
    @KafkaListener(topics = {TOPIC_PUBLISH})
    public void handlePublishMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            logger.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        //得到的消息没有问题，就开始处理事件
        //先查找到帖子，再将帖子保存到elasticsearch服务器
        DiscussPost post = discussPostService.findDiscussPostById(event.getEntityId());
        elasticsearchService.saveDiscussPost(post);
    }
}
