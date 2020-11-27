package com.luban.dao;

import com.luban.app.MyInvocationHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;

/**
 * 后置处理器 在放到BeanDefinitionMap之前执行
 */
public class MyAwareProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("indexDao")) {
			System.out.println("MyAwareProcessor 动态代理");
			bean = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Dao.class}, new MyInvocationHandler(bean));

		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return null;
	}

}
