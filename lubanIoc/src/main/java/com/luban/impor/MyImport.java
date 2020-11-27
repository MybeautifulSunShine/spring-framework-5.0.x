package com.luban.impor;

import com.luban.dao.MyAwareProcessor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 描述:
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-11-27 11:03
 */
public class MyImport implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{MyAwareProcessor.class.getName()};
	}
}
