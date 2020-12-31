package com.hgj.services;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-29 15:33
 */
@Component
public class P implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		AbstractBeanDefinition x = (AbstractBeanDefinition) beanFactory.getBeanDefinition("x");
		x.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE);
	}
}
