package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.dao.IndexDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 描述:
 * 		源码追踪1
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-11-19 14:25
 */
public class Test {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext();
		applicationContext.register(Appconfig.class);
		applicationContext.refresh();
		IndexDao bean = applicationContext.getBean(IndexDao.class);
		bean.query();
	}
}
