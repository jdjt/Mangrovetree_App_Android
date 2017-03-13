package com.jdjt.mangrove.http;


import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrovetreelibray.ioc.annotation.InNet;
import com.jdjt.mangrovetreelibray.ioc.annotation.InParam;
import com.jdjt.mangrovetreelibray.ioc.annotation.InPost;

/**
 * 网络接口范例 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 *
 * @author gdpancheng@gmail.com 2015年1月7日 下午2:16:21
 * @param <T>
 */
@InNet(Constant.HttpUrl.class)
public interface HttpInterFace {

//	//---------------------------------------------------------------------------------------
//	//同步
//	@InForm(Constant.HttpUrl.FILES)
//	public <T> T login(@InParam("id") String id, LinkedHashMap<String, File> params);
//
//	@InGet
//	public <T> T login(LinkedHashMap<String, Object> params);
//
//	@InPost
//	public <T> T asyncLoginPost(String name, String password);
//
//	@InPost(Constant.HttpUrl.LOGIN)
//	public <T> T postStr(@InParam String json);
//
//	@InWeb(method="getRegionCountry",space="http://WebXml.com.cn/")
//	public <T> T asyncWeb();
//
//	@InWeb
//	public <T> T asyncLoginWeb(@InParam("theRegionCode") String theRegionCode, NetConfig config);
//
	//---------------------------------------------------------------------------------------
//	//异步
//	@InPost(Constant.HttpUrl.LOGIN)
//	public void login(@InParam("account") String name, @InParam("password") String password);
	//异步
	@InPost(Constant.HttpUrl.LOGIN)
	public void login(@InParam() String json);

//	@InPost(Constant.HttpUrl.LOGIN)
//	public void loginPost(@InParam LinkedHashMap<String, Object> params);
//
//	@InPost
//	public void login(@InParam String json);
//
//	@InWeb
//	public void LoginWeb(String json);
//
//	@InForm
//	public void LoginForm(String name, String password);
}
