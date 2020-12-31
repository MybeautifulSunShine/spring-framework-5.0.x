package com.hgj.beandefinition;

import com.hgj.anno.HgjScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 描述:
 * 自定义的扫描器
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-08 11:48
 */
public class CustomScanner extends ClassPathBeanDefinitionScanner {
	@Override
	public void addIncludeFilter(TypeFilter includeFilter) {
		super.addIncludeFilter(new AnnotationTypeFilter(HgjScanner.class));
	}

	public CustomScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}
}
