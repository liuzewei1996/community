package com.liu.community.controller;

import com.liu.community.entity.DiscussPost;
import com.liu.community.entity.Page;
import com.liu.community.entity.User;
import com.liu.community.service.DiscussPostService;
import com.liu.community.service.LikeService;
import com.liu.community.service.UserService;
import com.liu.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    LikeService likeService;//添加点赞的逻辑：更新首页上面帖子的点赞数量；

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        // 方法调用之前,SpringMVC会自动实例化Model和Page,并将Page注入Model.
        // 所以,在thymeleaf中可以直接访问Page对象中的数据.
        page.setRows(discussPostService.findDiscussPostRows(0));//数据库中discuss总的数目
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);

                long likeCount  =  likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());//添加点赞数量
                map.put("likeCount",likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);//将一个discussPosts对象参数传入model对象
        return "/index";
    }
    @RequestMapping(path = "/index1", method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        // 方法调用钱,SpringMVC会自动实例化Model和Page,并将Page注入Model.
        // 所以,在thymeleaf中可以直接访问Page对象中的数据.
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, 0, 10);
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);//每一个帖子
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);//每一个帖子对应的每一个用户
                discussPosts.add(map);
            }
        }
        list.stream().forEach(i->{
            Map<String, Object> map = new HashMap<>();
            map.put("post", i);
            User user = userService.findUserById(i.getUserId());
            map.put("user", user);
            discussPosts.add(map);
        });
        model.addAttribute("discussPosts", discussPosts);
        return "/index";
//        Html文件中：Map.user.headerUrl---->map.get(“user”)  user  user.getHeaderUrl()
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }

    // 拒绝访问时的提示页面
    @RequestMapping(path = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "/error/404";
    }

}
