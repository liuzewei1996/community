package com.liu.community.service;

import com.liu.community.dao.UserMapper;
import com.liu.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: community
 * @Package: com.liu.community.service
 * @ClassName: UserService
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/17 18:52
 * @Version: 1.0
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int userId) {
        return userMapper.selectById(userId);
    }
}
