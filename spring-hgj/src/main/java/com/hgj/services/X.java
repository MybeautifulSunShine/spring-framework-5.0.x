package com.hgj.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-29 13:33
 */
@Component
public class X {

//	@Autowired
//	I y;
	//理论上是设置属性 ----- >约定的
	//实际上就是一个简单方法 -->spring认为是设置属性的
	public void setY(Y y) {
		System.out.println("XXXXX");
	}

}
