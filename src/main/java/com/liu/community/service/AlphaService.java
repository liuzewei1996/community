package com.liu.community.service;

import com.liu.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ProjectName: community
 * @Package: com.liu.community.Service
 * @ClassName: AlphaService
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/14 22:20
 * @Version: 1.0
 */
@Service
//@Scope("prototype")
public class AlphaService {
    //测试管理Bean：自动调用初始化以及销毁方法
    //类加载过程：加载----连接（验证、准备（为类变量分配内存设置默认值）、解析）---
    // 初始化（为类变量赋正确初始值）----类的实例化（都至少生成一个类的实例初始化方法<init>）
    //注：创建类对象时：创建新的对象，开辟内存空间--压入栈顶--先调用执行构造方法--
    // 针对在堆上所生成的对象的引用，将此引用值返回，即将引用存储在局部变量表中；
    //可以得到顺序为：：======》实例化(构造方法)--初始化--生成的对象--销毁AlphaService
    public AlphaService() {//构造方法
        System.out.println("实例化AlphaService");
    }

    @PostConstruct//声明为在构造方法之后执行
    public void init(){
        System.out.println("初始化AlphaService");
    }
    @PreDestroy//声明为在对象销毁之前执行
    public void destroy(){
        System.out.println("销毁AlphaService");
    }

    @Autowired
    public AlphaDao alphaDao;
    public String find(){
        return alphaDao.select();
    }

}
