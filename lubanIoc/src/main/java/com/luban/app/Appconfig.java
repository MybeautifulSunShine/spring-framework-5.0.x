package com.luban.app;

import com.luban.anno.EanbleHgj;
import com.luban.dao.IndexDao;
import com.luban.dao.IndexDao2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan({"com.luban.merge"})
@Configuration
//@Import(MyImport.class)
//@EanbleHgj
//@Import(MyImport.class)
public class Appconfig {

//	@Bean
//	public IndexDao2 indexDao1() {
//
//		return new IndexDao2();
//	}
//
//
//	@Bean
//	public IndexDao indexDao() {
//		//在这里进行拦截
//		indexDao1();
////		indexDao1();
//		return new IndexDao();
//	}
}
