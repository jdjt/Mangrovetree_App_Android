package com.jdjt.mangrove.activity.demo;

import android.util.Log;

import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrovetreelibray.utils.Handler_SharedPreferences;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:LoginApi
 * @Package com.jdjt.mangrove.activity.demo
 * @Date 2017/3/9 17:59
 */
public interface LoginApi {
    String BASE_URL = "http://mws.mymhotel.com/";


    @Headers("Cache-Control: public, max-age=6")
    @POST("uum/mem/sso/login.json")
    Call<Map> login(@Path("account") String account, @Path("password") String password);
}
