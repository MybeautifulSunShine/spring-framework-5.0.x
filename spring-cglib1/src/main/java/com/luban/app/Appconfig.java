package com.luban.app;

import com.luban.service.OrderService;
import com.luban.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.luban")
//@ImportResource("classpath:spring.xml")
public class Appconfig {
	@Bean
	public UserService userService() {
		return new UserService();
	}

	@Bean
	public OrderService orderService() {
		userService();
		return new OrderService();
	}
}
