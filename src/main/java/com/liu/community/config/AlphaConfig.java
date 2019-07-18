package com.liu.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @ProjectName: community
 * @Package: com.liu.community.config
 * @ClassName: AlphaConfig
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/15 20:40
 * @Version: 1.0
 */
@Configuration//声明配置
public class AlphaConfig {
    @Bean
    public SimpleDateFormat simpleDateFormat(){//方法名即为bean的名称
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
