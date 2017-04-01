package com.jdjt.mangrovetreelibray.ioc.net;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2015年1月4日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class IocHttpInvocationHandler implements InvocationHandler {
	private WeakReference<Object> object;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		IocHttp http = new IocHttp();
		return http.deal(proxy, method, args, object==null?object:object.get());
	}

	public Object getObject() {
		return object==null?object:object.get();
	}

	public void setObject(Object object) {
		this.object = new WeakReference<Object>(object);
	}


}
