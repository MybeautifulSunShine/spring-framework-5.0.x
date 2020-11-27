package com.luban.dao;

public class IndexDao2 {
	/**
	 * 获取到原型的bean 问题怎么获取的? 因为Spring把他添加进去了
	 */

	public IndexDao2() {
		System.out.println("initIndexDao2构造!!");
	}

	public void init() {
		System.out.println("init");
	}


}
