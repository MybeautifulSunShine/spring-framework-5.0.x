package com.hgj.test;

import com.hgj.app.Appconfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext();
		applicationContext.register(Appconfig.class);

//		applicationContext.addBeanFactoryPostProcessor(new HGBeanFactoryProcess());

//		beanFactory.setAllowCircularReferences(false);
		applicationContext.refresh();
//		applicationContext.getBean("x");
//		applicationContext.getBean("orderService");

//		System.out.println(applicationContext.getBean(E.class));
//		CustomScanner scanner = new CustomScanner(applicationContext);
//		scanner.addIncludeFilter(null);
//		int scan = scanner.scan("com.hgj");
//		System.out.println(scan);

//		ac.getBean("");
//		Proxy.newProxyInstance();

//		UserDao target = new UserDaoImpl();
//
//		UserDao pxory = new $ProxyLuban(target);
//		pxory.query();
//		ZLService zlService = new ZLServiceImpl();
//		ZLService proxy = new $ProxyLuban(zlService);
//		proxy.update("aaa");
//		UserDao mapper = (UserDao) MyBeanFactory.getMapper(UserDao.class);
//		mapper.qyery();

//	}
	}

}
