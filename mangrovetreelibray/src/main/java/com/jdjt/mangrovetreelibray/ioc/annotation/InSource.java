package com.jdjt.mangrovetreelibray.ioc.annotation;

import com.jdjt.mangrovetreelibray.ioc.util.LoonConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * activity生命周期注解类
 *
 * @author gdpancheng@gmail.com 2013-10-22 下午1:34:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InSource {

	/**
	 * @return
	 */
	int value() default LoonConstant.Number.ID_NONE;
}
