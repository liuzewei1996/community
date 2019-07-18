package com.liu.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: community
 * @Package: com.liu.community.Dao
 * @ClassName: AlphdaoMybatisImpl
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/14 22:05
 * @Version: 1.0
 */
@Repository
@Primary
public class AlphadaoMyBatisImpl implements AlphaDao{

    @Override
    public String select() {
        return "MyBatis";
    }
}
