package com.hgj.beanDetiontion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-10 13:35
 */
@Component
public class OrderService {
	@Autowired
	UserService userService;

	public OrderService() {
		System.out.println("start order");
	}
}
