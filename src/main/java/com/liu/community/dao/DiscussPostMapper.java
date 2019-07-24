package com.liu.community.dao;

import com.liu.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: community
 * @Package: com.liu.community.dao
 * @ClassName: DiscussMapper
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/17 17:00
 * @Version: 1.0
 */
@Mapper
public interface DiscussPostMapper {
    //帖子分页功能的实现
    //offset每一页起始行的行号；limit每一页最多显示的数目；
    // userId是动态的，有时候用到（当点击我的帖子的时候会通过它来实现），有时候不用
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    //使用@Param注解可以为userId这个参数取一个别名如 @Param("userId") int uId
    //并且在只有一个参数，且是动态使用的时候（有时候不用此参数），在<if>里使用，必须加上此注解
    int selectDiscussPostRows(@Param("userId") int userId);
    //discuss_post表中的行数，参数为0时查询这个表的行数；参数为userId时查这个表中userId的的个数

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

}
