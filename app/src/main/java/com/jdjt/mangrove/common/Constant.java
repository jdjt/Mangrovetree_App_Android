package com.jdjt.mangrove.common;

/*
 * Author: wmy
 * Description:
 * History:
 */
public class Constant {


	public static class HttpUrl {
		/** 系统配置的数据库名 **/

		public static final String DATA_USER="User";
        public static final String BASE = "http://mws.mymhotel.com/";// 服务器 外网
//		public static final String BASE = "http://rc-ws.mymhotel.com/";
		public static final String LOGIN = BASE+ "uum/mem/sso/login.json";
		/**
		 * 获取会员信息
		 */
		public static final String GETUSERINFO = BASE + "uum/mem/account/member_info.json";
		/**
		 * 用户退出
		 */
		public static final String METHOD_LOGOUT = BASE + "uum/mem/sso/logout.json";

		public static final int LOGIN_KEY = 0;
		public static final int GETUSERINFO_KEY=11;

	}
}
