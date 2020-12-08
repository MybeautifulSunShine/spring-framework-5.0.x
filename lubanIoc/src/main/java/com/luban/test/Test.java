package com.luban.test;

import com.luban.app.Appconfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//import com.luban.beanFactory.MyBeanFactoryProcess;

/**
 * 描述:
 * 源码追踪1
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
//		applicationContext.addBeanFactoryPostProcessor(new MyBeanFactoryProcess());
		applicationContext.refresh();
//		applicationContext.scan("");
//		Dao bean = (Dao) applicationContext.getBean("indexDao2");
//		bean.query();
//		CustomScanner scanner = new CustomScanner(applicationContext);
//		scanner.addIncludeFilter(null);
//		scanner.scan("com.luban");
//		IndexDao bean1 = applicationContext.getBean(IndexDao.class);
//		System.out.println(bean.hashCode() + "--------" + bean1.hashCode());
//		bean.query();
//		applicationContext.getBean(IndexDao2.class).init();
//		Dao index1 = (Dao) applicationContext.getBean("indexDao");
//		index1.query();
//		Appconfig appConfig = (Appconfig) applicationContext.getBean("appconfig");
//		System.out.println(appConfig);
//		Enhancer enhancer = new Enhancer();
//		//增强父类，地球人都知道cglib是基于继承来的
//		enhancer.setSuperclass(IndexDao.class);
//		enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
//		enhancer.setCallback(new MyTestMethodCallBack());
//		IndexDao indexDao = (IndexDao) enhancer.create();
//
//		indexDao.query();
//		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\class");
	}
}
