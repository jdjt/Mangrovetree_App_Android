package com.jdjt.mangrovetreelibray.ioc.annotation;

import com.jdjt.mangrovetreelibray.ioc.util.LoonConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类注解，应用在以下场景<br>
 * 1 fragment变量自动生成<br>
 * 2 adapter变量自动注入<br>
 * 3 组件
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2015年1月11日 下午4:31:42
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InBean {
	public int value() default LoonConstant.Number.ID_NONE;
}