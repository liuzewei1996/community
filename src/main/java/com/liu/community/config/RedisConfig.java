package com.liu.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

/*
* @Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一
* 个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext
* 或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
* */
//实际上，Spring很好的实现了泛型依赖注入<String, Object>
// https://blog.csdn.net/f641385712/article/details/84679147

    /*
    * StringRedisTemplate与RedisTemplate的区别
    两者的关系是StringRedisTemplate继承RedisTemplate。
    两者的数据是不共通的；也就是说StringRedisTemplate只能管理StringRedisTemplate里面的数据，
    RedisTemplate只能管理RedisTemplate中的数据。
    默认采用的序列化策略有两种，一种是String的序列化策略，一种是JDK的序列化策略。
    StringRedisTemplate默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的。
    RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的。
    * */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        //定义一个bean，方法名为redisTemplate；当声明了这个bean方法，
        // 参数为RedisConnectionFactory，参数也是一个Bean，它会被自动注入进来，被容器装配
        //都配好后，就可以通过redisTemplate访问redis了
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);//template具有了访问数据库的能力

        //写Java程序得到Java类型数据是要存到数据库中，所以要指定一种数据转化的方式

        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        // value本身为hash时：设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        template.afterPropertiesSet();//触发生效
        return template;
    }

}
