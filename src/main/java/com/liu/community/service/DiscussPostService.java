package com.liu.community.service;

import com.liu.community.dao.DiscussPostMapper;
import com.liu.community.entity.DiscussPost;
import com.liu.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @ProjectName: community
 * @Package: com.liu.community.service
 * @ClassName: DiscussService
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/17 18:46
 * @Version: 1.0
 */
@Service
public class DiscussPostService {
    //页面显示的时候肯定不会显示userId，是要显示用户名称
    // 有这两种方式：1可以在写SQL是关联查询用户
    // 2得到discussPost数据后单独查询一下user,再组装起来
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;//用来过滤敏感词

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    //加入一条帖子
    public int addDiscussPost(DiscussPost post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 先转义HTML标记
//        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
//        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 再过滤敏感词
        post.setTitle(sensitiveFilter.filter(HtmlUtils.htmlEscape(post.getTitle())));
        post.setContent(sensitiveFilter.filter(HtmlUtils.htmlEscape(post.getContent())));
//        post.setTitle(sensitiveFilter.filter(post.getTitle()));
//        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%增加置顶、加精、删除的方法
    public int updateType(int id, int type){
        return discussPostMapper.updateType(id, type);
    }
    public int updateStatus(int id, int type){
        return discussPostMapper.updateStatus(id, type);
    }



}
