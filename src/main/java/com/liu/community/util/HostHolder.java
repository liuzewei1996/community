package com.liu.community.util;

import com.liu.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息,用于代替session对象.
 */
@Component
public class HostHolder {
    //主要起到一个容器的作用
    private ThreadLocal<User> users = new ThreadLocal<>();
    //ThreadLocal是以线程为key存取值的；获取当前线程，达到当前线程的一个map，根据得到的map来存取值

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
