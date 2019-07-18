package com.liu.community.dao;

import com.liu.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    //通过id来查询
    User selectById(int id);
    //通过用户名来查询
    User selectByName(String username);
    //通过email来查询
    User selectByEmail(String email);
    //添加用户
    int insertUser(User user);
    //更新状态
    int updateStatus(int id, int status);
    //更新头像
    int updateHeader(int id, String headerUrl);
    //更新密码
    int updatePassword(int id, String password);

}
