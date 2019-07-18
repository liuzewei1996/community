package com.liu.community;

import com.liu.community.dao.AlphaDao;
import com.liu.community.service.AlphaService;
import com.liu.community.config.AlphaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Test
	public void contextLoads() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	@Test
	public void testApplicationContext(){//这些演示的是主动来获取，比较笨拙的方式
		System.out.println(applicationContext);
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);//通过类型获取
		System.out.println(alphaDao.select());
		//依赖的是接口；容器自动装配实现类
		//没有添加AlphadaoMyBatisImpl实现类时，输出：Hibernate
		//在添加AlphadaoMyBatisImpl实现类后，设置@Primary ，输出 MyBatis

		alphaDao = applicationContext.getBean("alphaHibernate",AlphaDao.class);//通过名称获取
		System.out.println(alphaDao.select());

	}
	//测试管理Bean：自动调用初始化以及销毁方法
	//被Spring容器使用的Bean是默认单例的，它在程序中只被实例化一次，只被销毁一次
	// （可以使用@Scope("singleton")(默认)，@Scope("prototype")来设置多个Bean的实例）
	@Test
	public void testManagement(){//这些演示的是主动来获取，比较笨拙的方式；目的是演示spring容器的获取等等
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);//通过名称获取
		System.out.println(alphaService);
		alphaService = applicationContext.getBean(AlphaService.class);//通过名称获取
		System.out.println(alphaService);

	}
	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	//直接依赖注入的方式：
	@Autowired
	@Qualifier("alphaHibernate")//用这个可以指定是实现了AlphaDao的Bean的名称
	private AlphaDao alphaDao;
	@Autowired
	private AlphaService alphaService;
	@Autowired
	private AlphaConfig alphaConfig;
 	@Test
	public void testDI(){//这种：依赖注入的方式注入进来
		System.out.println(alphaDao);
		System.out.println(alphaService);
		System.out.println(alphaConfig);
	}

}
