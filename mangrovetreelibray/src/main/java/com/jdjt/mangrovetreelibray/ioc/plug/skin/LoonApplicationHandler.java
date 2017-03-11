package com.jdjt.mangrovetreelibray.ioc.plug.skin;

import com.google.dexmaker.ProxyBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 拦截器 对所有需要自动化注解的类 进行拦截 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 *
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:17:33
 */
public class LoonApplicationHandler implements InvocationHandler {

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		return ProxyBuilder.callSuper(proxy, method, args);
	}
}
