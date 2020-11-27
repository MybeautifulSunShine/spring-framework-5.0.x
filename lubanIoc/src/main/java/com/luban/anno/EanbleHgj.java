package com.luban.anno;

import com.luban.impor.MyImport;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

@Import(MyImport.class)
public @interface EanbleHgj {
}
