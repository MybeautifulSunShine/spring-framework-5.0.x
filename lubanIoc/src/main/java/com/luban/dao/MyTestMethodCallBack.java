package com.luban.dao;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 描述: 对Cjlib的方法拦截器 进行拦截
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-11-30 15:23
 */
public class MyTestMethodCallBack implements MethodInterceptor {
	/**
	 * @param o           代理对象
	 * @param method      目标的方法
	 * @param objects     参数
	 * @param methodProxy 代理方法
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("MyTestMethodCallBack 方法拦截");
		return methodProxy.invokeSuper(o, objects);
	}
}
