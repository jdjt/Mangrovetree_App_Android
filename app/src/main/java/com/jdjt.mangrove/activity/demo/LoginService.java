package com.jdjt.mangrove.activity.demo;

import com.jdjt.mangrovetreelibray.http.Retrofit2Client;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:LoginService
 * @Package com.jdjt.mangrove.activity.demo
 * @Date 2017/3/9 18:11
 */
public enum LoginService {
    INSTANCE;

    private LoginApi loginApi;

    LoginService() {
        loginApi = Retrofit2Client.INSTANCE
                .getRetrofitBuilder()
                .baseUrl(loginApi.BASE_URL).build()
                .create(LoginApi.class);
    }

    public LoginApi getLoginApi() {
        return loginApi;
    }
}
