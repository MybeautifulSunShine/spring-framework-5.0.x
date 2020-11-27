/*
package com.luban.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IndexDao implements ApplicationContextAware {
	*/
/**
	 * 获取到原型的bean 问题怎么获取的? 因为Spring把他添加进去了
	 *//*

	private ApplicationContext applicationContext;

	public IndexDao() {
		System.out.println("initIndexDao构造!!");
	}

	@PostConstruct
	public void init() {
		System.out.println("init");
	}

	public void query() {
		System.out.println("query");
		applicationContext.getBean("indexDao");
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
*/
