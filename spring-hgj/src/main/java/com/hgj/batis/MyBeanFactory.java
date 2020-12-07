package com.hgj.batis;

import java.lang.reflect.Proxy;

/**
 * 描述:
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-04 17:38
 */
public class MyBeanFactory {
	public static Object getMapper(Class clazz) {
		/**
		 * (ClassLoader loader, 动态加载一个类
		 * Class<?>[] interfaces, 指定放回什么借口
		 * InvocationHandler h  代理类实现接口后的具体的逻辑 实现类之后实现的类
		 */
		Class[] classes = new Class[]{clazz};
		Object o = Proxy.newProxyInstance(MyBeanFactory.class.getClassLoader(), classes, new MyInvocationHandler());
		return o;
	}
}
