package com.liu.community.controller;

import com.liu.community.entity.Event;
import com.liu.community.entity.User;
import com.liu.community.event.EventProducer;
import com.liu.community.service.LikeService;
import com.liu.community.util.CommunityConstant;
import com.liu.community.util.CommunityUtil;
import com.liu.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConstant {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId) {
        //点赞用异步请求来实现；在前端页面，点击超链接访问服务器；需要用js方法来实现点赞提交请求的逻辑
        //前端页面discuss_detail.html：onclick="|like(this,1,${post.id},${post.userId});|"
        //discuss.js: function like(btn, entityType, entityId)
        //===========添加kafka重构：在传入参数的时候添加了一个参数：postId================
        // {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId}

        User user = hostHolder.getUser();

        // 点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);

        // 数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        // 返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        //添加kafka系统通知部分：触发点赞事件
        //赞的时候发布通知；取消赞的时候就不用发布通知了
        if(likeStatus == 1){//点赞
            Event event = new Event().setTopic(TOPIC_LIKE)
                    .setEntityId(entityId)
                    .setEntityType(entityType)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);

            eventProducer.fireEvent(event);
        }

        return CommunityUtil.getJSONString(0, null, map);
    }

}
