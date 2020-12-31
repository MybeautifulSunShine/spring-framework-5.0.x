package com.hgj.beandefinition;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author HeGaoJian
 * @version 1.0
 * @create 2020-12-08 23:28
 */
@Component
public class MyBeanFactoryProcess implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		GenericBeanDefinition orderService = (GenericBeanDefinition) beanFactory.getBeanDefinition("orderService");
		orderService.setInstanceSupplier(() -> new OrderService(beanFactory.getBean(UserService.class)));
//		orderService.getConstructorArgumentValues().addGenericArgumentValue(beanFactory.getBean("userService"));
//		orderService.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
	}
}
