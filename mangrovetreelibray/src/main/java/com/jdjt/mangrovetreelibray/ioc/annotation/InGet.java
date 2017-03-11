package com.jdjt.mangrovetreelibray.ioc.annotation;

import com.jdjt.mangrovetreelibray.ioc.util.LoonConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InGet {

	public String value() default "";

	public int type() default LoonConstant.Number.ID_NONE;

}