package com.jdjt.mangrove.application;

import android.app.Application;
import android.support.annotation.Keep;
import android.util.Log;

import com.fengmap.android.FMMapSDK;
import com.fengmap.drpeng.common.ResourcesUtils;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrove.http.HttpInterFace;
import com.jdjt.mangrovetreelibray.ioc.annotation.NotProguard;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.net.IocHttpListener;
import com.jdjt.mangrovetreelibray.ioc.net.IocListener;
import com.jdjt.mangrovetreelibray.ioc.net.NetConfig;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.InternetConfig;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;
import com.jdjt.mangrovetreelibray.ioc.util.Http;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:MangrovetreeApplication
 * @Package com.jdjt.mangrovetree.application
 * @Date 2017/3/9 11:30
 */
public class MangrovetreeApplication extends Application {
    public static MangrovetreeApplication instance;
    public Http<HttpInterFace> http;
    //全局拦截器
    public IocHttpListener<ResponseEntity> listener = null;

    @Override
    public void onCreate() {
        instance = this;
        plugLoad();

        FMMapSDK.init(this, ResourcesUtils.getSDPath() + "/fm_drpeng");
        super.onCreate();
    }

    // 增加一个自动获取照片的第三方库
    @Keep
    public void plugLoad() {
        // ------------------------------------------------------------------
        // 从本地或者摄像头获取照片模块
//        PlugConstants.setPlugIn(new PhotoParameter());
        // 登录模块
//        PlugConstants.setPlugIn(new LoginParameter());
        // ------------------------------------------------------------------
        // 框架只是实现了分发，具体核心请求模块还必须用自己或者第三方网络请求框架
        // 网络请求的统一拦截处 异步请求 请返回 请求的值 异步 手动分发
        http = new Http<HttpInterFace>(HttpInterFace.class);
        listener = new IocHttpListener<ResponseEntity>() {
            @Override
            @NotProguard
            public ResponseEntity netCore(NetConfig config) {
                Ioc.getIoc().getLogger().i("拦截请求：" + config);
                Log.i("httpApi", "拦截请求:" + config);
//            config.setHead(HeaderConst.inHeaders());
                InternetConfig netConfig = InternetConfig.defaultConfig();
                netConfig.setHead(HeaderConst.inHeaders());
                netConfig.setKey(config.getCode());
                ResponseEntity reslut = null;
//
                try {
                    switch (config.getType()) {
                        case GET:
                            reslut = FastHttp.get(config.getUrl(), config.getParams(), netConfig);
                            break;
                        case POST:
                            reslut = FastHttp.postString(config.getUrl(), config.getParam(), netConfig);
                            break;
                        case FORM:
                            reslut = FastHttp.form(config.getUrl(), config.getParams(), new HashMap<String, File>(), netConfig);
                            break;
                        case WEB:
                            netConfig.setMethod(config.getMethod());
                            netConfig.setName_space(config.getName_space());
                            reslut = FastHttp.webServer(config.getUrl(), config.getParams(), netConfig, "post");
                            break;
                    }
//                    Ioc.getIoc().getLogger().i("拦截结果：" + reslut);
                    Log.i("httpApi", "拦截结果:" + reslut);
                } catch (Exception exception) {
                    exception.printStackTrace();
//                    Ioc.getIoc().getLogger().i("请求超时：" + reslut);
                    Log.i("httpApi", "拦截结果:" + reslut);
                }
                return reslut;
            }
        };
        IocListener.newInstance().setHttpListener(listener);
    }

    int ERROR = -1;
    int ok = 0;
    public IocHttpListener<ResponseEntity> listener2 = new IocHttpListener<ResponseEntity>() {

        final OkHttpClient client = new OkHttpClient();

        @Override
        public ResponseEntity netCore(NetConfig config) {
            System.out.println("拦截请求：" + config);
            ResponseEntity responseEntity = new ResponseEntity();
            switch (config.getType()) {
                case POST: {
                    try {
                        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
                        RequestBody requerstBody = RequestBody.create(mediaType, config.getParam());
                        Request.Builder builder = new Request.Builder().url(config.getUrl());
                        String ticket = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "ticket", 0);
                        builder.addHeader(HeaderConst.MYMHOTEL_TICKET, ticket);
                        builder.addHeader(HeaderConst.MYMHOTEL_TYPE, "1001");
                        builder.addHeader(HeaderConst.MYMHOTEL_VERSION, "1");
                        builder.addHeader(HeaderConst.MYMHOTEL_DATATYPE, "JSON");
                        builder.addHeader(HeaderConst.MYMHOTEL_SOURCECODE, "");
                        builder.addHeader(HeaderConst.MYMHOTEL_DATETIME, new Date().getTime() + "");
                        builder.addHeader(HeaderConst.MYMHOTEL_ACKDATATYPE, "JSON");
                        builder.post(requerstBody);
                        Request request = builder.build();
                        Response response = client.newCall(request).execute();
                        if (!response.isSuccessful()) {
                            responseEntity.setStatus(FastHttp.result_net_err);
                            break;
                        }
                        Headers responseHeaders = response.headers();
                        responseEntity.setUrl(config.getUrl());
                        responseEntity.setKey(config.getCode());
                        responseEntity.setContent(response.body().string(), false);
                        responseEntity.setStatus(FastHttp.result_ok);
                        responseEntity.setJsonParams(config.getParam());
                        responseEntity.setHeaders(outHeaders(responseHeaders));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            Log.i("httpApi", "拦截结果:" + responseEntity);
            return responseEntity;
        }
    };


    private static Map<String, Object> outHeaders(Headers resHeaders) throws UnsupportedEncodingException {

        // 取出头部信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_TYPE,
                resHeaders.get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_TYPE) == null ? ""
                        : resHeaders.get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_TYPE));
        map.put(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_VERSION, resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_VERSION) == null ? "" : resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_VERSION));
        map.put(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_DATATYPE, resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_DATATYPE) == null ? "" : resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_DATATYPE));
        map.put(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_SOURCECODE, resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_SOURCECODE) == null ? "" : resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_SOURCECODE));
        map.put(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_DATETIME, resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_DATETIME) == null ? "" : resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_DATETIME));
        map.put(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_STATUS, resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_STATUS) == null ? "" : resHeaders
                .get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_STATUS));

        String msg = resHeaders.get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_MESSAGE) == null ? ""
                : resHeaders.get(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_MESSAGE);
        map.put(com.jdjt.mangrovetreelibray.ioc.util.HeaderConst.MYMHOTEL_MESSAGE, msg);
        // 设置输出头部参数信息
        Ioc.getIoc().getLogger().i("mssage转换后:" + msg);
        return map;
    }
}
