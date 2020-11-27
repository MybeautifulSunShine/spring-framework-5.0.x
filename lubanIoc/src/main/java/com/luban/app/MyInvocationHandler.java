package com.luban.app;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 描述:
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-11-27 11:26
 */
public class MyInvocationHandler implements InvocationHandler {
	Object targer;

	public MyInvocationHandler(Object targer) {
		this.targer = targer;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("wo shi dai li fang fa!!!");
		return method.invoke(targer, args);
	}
}
