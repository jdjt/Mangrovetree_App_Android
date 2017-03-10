package com.jdjt.mangrovetreelibray.ioc.annotation;

import com.jdjt.mangrovetreelibray.ioc.listener.OnListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 事件注解类
 * @author gdpancheng@gmail.com 2013-10-22 下午1:33:57
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InListener {

	/**
	 * @return
	 */
	int[] ids();

	/**
	 * @return
	 */
	Class<? extends OnListener>[] listeners();

}
