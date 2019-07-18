package com.liu.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @ProjectName: community
 * @Package: com.liu.community.Dao
 * @ClassName: AlphadaoHibernateImpl
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/14 22:00
 * @Version: 1.0
 */
@Repository("alphaHibernate")//添加参数("alphaHibernate")自定义类的名字
public class AlphadaoHibernateImpl implements AlphaDao{

    @Override
    public String select() {
        return "Hibernate";
    }
}
