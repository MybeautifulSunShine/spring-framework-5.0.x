package com.hgj.batis;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 描述:
 * 默认的代理类
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-04 17:48
 */
public class MyInvocationHandler implements InvocationHandler {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		System.out.println("conn db");
		Select annotation = method.getAnnotation(Select.class);
		String sql = annotation.value()[0];
		System.out.println(sql);
		Class<?> returnType = method.getReturnType();
		return returnType;
	}
}
