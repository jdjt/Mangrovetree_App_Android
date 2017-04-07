package com.jdjt.mangrovetreelibray.ioc.inject;

import android.graphics.drawable.Drawable;

import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-16
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class InjectDrawable extends InjectResouceType<Drawable> {

	@Override
    protected Drawable getResouce(int id) {
//        getResources().
		return Ioc.getIoc().getApplication().getApplicationContext().getDrawable(id);
    }
}
