package com.jdjt.mangrovetreelibray.ioc.validator;

import android.widget.TextView;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年12月2日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class VaMobile extends ValidatorCore implements VaRule {

	/**
	 * 验证之前的初始化
	 */
	public void init() {
		setMsg("手机号码格式不正确");
	}

	public boolean check() {
		String str = ((TextView)(getView())).getText().toString().trim();
		if (super.check()) {
	        return !Regex.StrisNull(str)&&Regex.isMobile(str);
        }
		return true;
	}
}
