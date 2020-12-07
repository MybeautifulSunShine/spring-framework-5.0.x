package com.hgj.test;

import com.hgj.app.Appconfig;
import com.hgj.batis.MyProxy;
import com.hgj.services.ZLService;
import com.hgj.target.ZLServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class Test {

	public static void main(String[] args) {
//		ApplicationContext ac = new AnnotationConfigApplicationContext(Appconfig.class);
//		ac.getBean("");
//		Proxy.newProxyInstance();

		try {
			ZLService proxy = (ZLService) MyProxy.getInstance(new ZLServiceImpl());
			proxy.update("XXXX");
		} catch (Exception e) {
//			e.printStackTrace();
		}
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
