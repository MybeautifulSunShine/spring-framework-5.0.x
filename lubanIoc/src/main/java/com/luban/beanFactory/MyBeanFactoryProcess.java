package com.luban.beanFactory;

import com.luban.dao.IndexDao2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * 自定义的beanFactory参与bean的实例化过程
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-11-24 17:40
 */
@Component
public class MyBeanFactoryProcess implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanFactory.getBeanDefinition("indexDao");
		GenericBeanDefinition a = (GenericBeanDefinition)
				beanFactory.getBeanDefinition("indexDao");
		a.setBeanClass(IndexDao2.class);

//		annotatedBeanDefinition.setScope("prototype");
	}
}
