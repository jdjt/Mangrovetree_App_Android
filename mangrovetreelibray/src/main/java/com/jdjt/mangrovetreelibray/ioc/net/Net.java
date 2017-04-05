package com.jdjt.mangrovetreelibray.ioc.net;
/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年5月22日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
public enum Net {
	NONE(0),
	GET(1),
	POST(2),
	WEB(3),
	FORM(4);

	private int context;
	private int getContext(){
		return this.context;
	}
	private Net(int context){
		this.context = context;
	}
//	public static void main(String[] args){
//		for(Net name :Net.values()){
//			System.out.println(name+" : "+name.getContext());
//		}
//		System.out.println(Net.POST.getDeclaringClass());
//	}
}
