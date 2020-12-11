package com.hgj.app;

import com.hgj.test.E;
import com.hgj.test.F;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
@ComponentScan("com.hgj.beanDetiontion")
//@MapperScan
@Configuration
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
