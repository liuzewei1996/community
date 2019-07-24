package com.liu.community.service;

import com.liu.community.dao.AlphaDao;
import com.liu.community.dao.DiscussPostMapper;
import com.liu.community.dao.UserMapper;
import com.liu.community.entity.DiscussPost;
import com.liu.community.entity.User;
import com.liu.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public String find(){
        return alphaDao.select();
    }


//====================================spring处理事务====================================
//====================================在test中测试====================================

    // REQUIRED: 支持当前事务(外部事务),如果不存在则创建新事务.
    // REQUIRES_NEW: 创建一个新事务,并且暂停当前事务(外部事务).
    // NESTED: 如果当前存在事务(外部事务),则嵌套在该事务中执行(独立的提交和回滚),否则就会REQUIRED一样.
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Object save1() {
        // 新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 新增帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("Hello");
        post.setContent("新人报道!");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        Integer.valueOf("abc");//故意写一个错误的语句，这条语句报错；事务回滚，对数据库仍然保证一致性

        return "ok";
    }

    public Object save2() {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                // 新增用户
                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
                user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
                user.setEmail("beta@qq.com");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);

                // 新增帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("你好");
                post.setContent("我是新人!");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

                Integer.valueOf("abc");

                return "ok";
            }
        });
    }

}
