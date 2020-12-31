package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.merge.ChildBean;
import com.luban.merge.RootBean;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 描述:
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-20 14:43
 */
public class MergeTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext();
		applicationContext.register(Appconfig.class);

		GenericBeanDefinition rootBeanDefinition = new GenericBeanDefinition();
		rootBeanDefinition.setBeanClass(RootBean.class);
		rootBeanDefinition.getPropertyValues().add("type", "动作片");
		rootBeanDefinition.getPropertyValues().add("name", "导火线");
		applicationContext.registerBeanDefinition("root", rootBeanDefinition);
//		rootBeanDefinition.setAbstract(true);
			GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setParentName("root");
		genericBeanDefinition.setBeanClass(ChildBean.class);
		genericBeanDefinition.getPropertyValues().add("name", "无间道");
		applicationContext.registerBeanDefinition("chil", genericBeanDefinition);
//		applicationContext.addBeanFactoryPostProcessor(new HGBeanFactoryProcess());

//		beanFactory.setAllowCircularReferences(false);
		applicationContext.refresh();
		System.out.println(applicationContext.getBean(ChildBean.class));
	}
}
