package com.jdjt.mangrovetreelibray.ioc.plug.login;

import com.jdjt.mangrovetreelibray.ioc.plug.PlugEntity;
import com.jdjt.mangrovetreelibray.ioc.plug.PlugParameter;

/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年11月6日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
public class LoginParameter implements PlugParameter {

	@Override
    public PlugEntity getEntity() {
		PlugEntity plug = new PlugEntity();
		plug.setClazz(PluginLogin.class);
		plug.addMethodName("save");
		plug.addMethodName("clear");
		plug.addMethodName("i");
		plug.addMethodName("onValiSucceeded");
		plug.addMethodName("onValiFailed");
		plug.addMethodName("getSave");
		plug.addMethodName("callBack");
		plug.setCallBack(new LoginCallBack());
	    return plug;
    }
}
