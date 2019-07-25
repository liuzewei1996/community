package com.liu.community.controller;

import com.liu.community.entity.Comment;
import com.liu.community.service.CommentService;
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
public class CommentController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;

    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());//如果用户没有登陆，这种异常后面会做统一的异常处理
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        return "redirect:/discuss/detail/" + discussPostId;

    }
}
