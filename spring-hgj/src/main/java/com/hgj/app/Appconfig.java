package com.hgj.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("com.hgj.beanDefinition")
//@MapperScan
@Configuration
//无论哪个接口都使用cglib
@EnableAspectJAutoProxy()
//@Import(CustomAopBeanFactoryProcess.class)
//@ImportResource("classpath:spring.xml")
public class Appconfig {

	/*@Bean
	public E e() {
		System.out.println("ee");
		f();
		return new E();
	}
	@Bean
	public F f() {
		System.out.println("ff");
		return new F();
	}*/

}
