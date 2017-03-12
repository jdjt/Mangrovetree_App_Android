package com.jdjt.mangrovetreelibray.ioc.util;

import android.util.Log;

import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;

import java.util.Date;
import java.util.HashMap;

public class HeaderConst {

	/**
	 * @用户会话ID 登录成功，代表在线用户的唯一身份
	 * @value mymhotel-ticket
	 */
	public static final String MYMHOTEL_TICKET ="mymhotel-ticket";

	/**
	 * @请求来源类型 11：app手机端（Android） 12:app手机端（IOS） 99:其他
	 * @响应来源类型 01:服务器（红树林管家） 99:其他
	 * @value mymhotel-type
	 */
	public static final String MYMHOTEL_TYPE = "mymhotel-type";

	/**
	 * @消息版本号
	 * @value mymhotel-version
	 */
	public static final String MYMHOTEL_VERSION = "mymhotel-version";

	/**
	 * @请求响应 数据类型：JSON
	 * @value mymhotel-dataType
	 */
	public static final String MYMHOTEL_DATATYPE = "mymhotel-dataType";

	/**
	 * @要求反馈的响应 数据类型 : JSON
	 * @value mymhotel-ackDataType
	 */
	public static final String MYMHOTEL_ACKDATATYPE = "mymhotel-ackDataType";

	/**
	 * @请求  发送方编码：手机端（Android、IOS）的场合，为手机的MAC地址。
	 * @响应  发送方编码 ： System
	 * @value mymhotel-sourceCode
	 */
	public static final String MYMHOTEL_SOURCECODE = "mymhotel-sourceCode" ;

	/**
	 * @发送时间  时间戳
	 * @value mymhotel-dateTime
	 */
	public static final String MYMHOTEL_DATETIME = "mymhotel-dateTime";

	/**
	 * @响应状态 正常场合：OK 错误场合：ERR
	 * @value mymhotel-status
	 */
	public static final String MYMHOTEL_STATUS = "mymhotel-status";
	/**
	 * @信息 提示&错误 格式：消息编码+”|”+消息内容
	 * @value mymhotel-message
	 */
	public static final String MYMHOTEL_MESSAGE = "mymhotel-message";



	public static HashMap<String, Object> inHeaders() {

		// 取出头部信息
		HashMap<String, Object> map = new HashMap<>();
		String ticket= Handler_SharedPreferences.getValueByName("User", "ticket",0);
//        if (Handler_String.isBlank(ticket)&&null!=MapVo.get("ticket")) {
//            Ioc.getIoc().getLogger().i("获取Handler_SharedPreferences 下的ticket 失败");
//            ticket = MapVo.get("ticket").toString();
//        }
		map.put(HeaderConst.MYMHOTEL_TICKET, ticket);
		map.put(HeaderConst.MYMHOTEL_TYPE, "1001");
		map.put(HeaderConst.MYMHOTEL_VERSION, "1");
		map.put(HeaderConst.MYMHOTEL_DATATYPE, "JSON");
		map.put(HeaderConst.MYMHOTEL_SOURCECODE, "");
		map.put(HeaderConst.MYMHOTEL_DATETIME, new Date().getTime() + "");
		map.put(HeaderConst.MYMHOTEL_ACKDATATYPE, "JSON");
		Log.i("LoginApi","输入头部参数信息:{" + map.toString() + "}");
		return map;
	}




}
