package com.hgj.beandefinition;


import com.hgj.cglib.CglibUtil;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

	public OrderService() {
		System.out.println("orderService");
	}
//
//	public OrderService(I userService) {
//		System.out.println("interface  service And userService ");
//	}

	public OrderService(UserService userService) {
		System.out.println("userService  Constr 1111111 ");
	}

	public OrderService(I userService, UserService service) {
		System.out.println("interface  service And userService 22222");
	}


}
