package com.liu.community.controller;

import com.liu.community.entity.Comment;
import com.liu.community.entity.DiscussPost;
import com.liu.community.entity.Event;
import com.liu.community.event.EventProducer;
import com.liu.community.service.CommentService;
import com.liu.community.service.DiscussPostService;
import com.liu.community.util.CommunityConstant;
import com.liu.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @ProjectName: community
 * @Package: com.liu.community.controller
 * @ClassName: CommentController
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/25 9:49
 * @Version: 1.0
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService  discussPostService;

    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());//如果用户没有登陆，这种异常后面会做统一的异常处理
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        //添加kafka系统通知部分：触发评论事件
        //消费的时候所需要的信息都设置进去，可能有些也用不上
        Event event =new Event().setTopic(TOPIC_COMMENT).
                setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId", discussPostId);//在页面上有连接：帖子id是为了能够连接到评论的帖子处
        //评论的对象实体的id：由于评论的可能是帖子也可能是评论，需要判断一下
        if(comment.getEntityType() == ENTITY_TYPE_POST){//如果是帖子
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }else if(comment.getEntityType() == ENTITY_TYPE_COMMENT){//如果是评论
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);
        //这样实现，在处理业务的同时，另外一个线程也在处理消息；异步的；这样处理的能力也就提高了；

        return "redirect:/discuss/detail/" + discussPostId;

    }
}
