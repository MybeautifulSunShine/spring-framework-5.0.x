package com.luban.dao;

import org.springframework.stereotype.Component;

@Component
public class IndexDao implements Dao {
	@Override
	public void query() {
		System.out.println("Indao1 query()");
	}

}
