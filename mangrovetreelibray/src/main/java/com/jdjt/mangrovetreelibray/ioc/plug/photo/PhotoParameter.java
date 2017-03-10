package com.jdjt.mangrovetreelibray.ioc.plug.photo;

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
public class PhotoParameter implements PlugParameter {

	@Override
    public PlugEntity getEntity() {
		PlugEntity plug = new PlugEntity();
		plug.setClazz(PluginPhoto.class);
		plug.addMethodName("camera");
		plug.addMethodName("photo");
		plug.addMethodName("onActivityResult");
		plug.setCallBack(new PhotoCallBack());
	    return plug;
    }
}
