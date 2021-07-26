package com.hgj.anno;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述:
 * 自建扫描器
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-08 11:53
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface HgjScanner {

}
