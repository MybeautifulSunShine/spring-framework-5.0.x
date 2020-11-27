package com.luban.app;

import com.luban.impor.MyImport;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan({"com.luban"})
//@Configuration
//@Import(MyImport.class)
//@EanbleHgj
@Import(MyImport.class)
public class Appconfig {
//
//	@Bean
//	public IndexDao1 indexDao1() {
//
//		return new IndexDao1();
//	}
//
//	@Bean
//	public IndexDao indexDao(){
//		indexDao1();
//		indexDao1();
//		return new IndexDao();
//	}
}
