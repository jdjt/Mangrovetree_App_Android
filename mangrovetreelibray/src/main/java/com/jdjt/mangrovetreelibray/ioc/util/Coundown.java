package com.jdjt.mangrovetreelibray.ioc.util;

import com.jdjt.mangrovetreelibray.ioc.countdown.IocCountDownTimer;


/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年12月4日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
public class Coundown {
	public static void start(int interval) {
		new IocCountDownTimer(Integer.MAX_VALUE,interval).start();
    }
}
