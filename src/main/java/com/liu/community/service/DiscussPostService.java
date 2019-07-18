package com.liu.community.service;

import com.liu.community.dao.DiscussPostMapper;
import com.liu.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
