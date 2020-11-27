/*
package com.luban.dao;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

*/
/**
 * 描述:
 * 验证BeanPostProcess的作用
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-11-20 13:49
 *//*

//@Component
public class TestIndexProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("indexDao")) {
			System.out.println("IndexDao实例化之前");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("indexDao")) {
			System.out.println("IndexDao实例化之后");
		}
		return bean;
	}
}
*/
