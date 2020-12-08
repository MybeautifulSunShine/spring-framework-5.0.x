package com.hgj.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
@ComponentScan("com.hgj")
@MapperScan("com.hgj")
//@ImportResource("classpath:spring.xml")
public class Appconfig {
}
