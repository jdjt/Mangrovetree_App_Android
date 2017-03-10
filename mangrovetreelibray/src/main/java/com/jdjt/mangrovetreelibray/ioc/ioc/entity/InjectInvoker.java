package com.jdjt.mangrovetreelibray.ioc.ioc.entity;

public interface InjectInvoker{

	public  <T> void invoke(T beanObject, Object... args);
}
