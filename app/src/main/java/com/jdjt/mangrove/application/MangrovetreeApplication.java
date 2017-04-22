package com.jdjt.mangrove.application;

import android.app.Application;
import android.os.Build;
import android.support.annotation.Keep;
import android.util.Log;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
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

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

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

    private void initIM() {
//必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
//第一个参数是Application Context
//这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            YWAPI.init(instance, Constant.ALI_APPKEY);
        }
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

    public IocHttpListener<ResponseEntity> listener2 = new IocHttpListener<ResponseEntity>() {

        final OkHttpClient client = new OkHttpClient();

        @Override
        public ResponseEntity netCore(final NetConfig config) {
            System.out.println("拦截请求：" + config);
            ResponseEntity responseEntity = new ResponseEntity();
            switch (config.getType()) {
                case POST: {
                    try {

                        final MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
                        RequestBody requerstBody = new RequestBody() {
                            @Override
                            public MediaType contentType() {
                                return mediaType;
                            }

                            @Override
                            public void writeTo(BufferedSink sink) throws IOException {
                                DataOutputStream out = new DataOutputStream(sink.outputStream());
                                if (null != config.getParam())
                                    out.writeBytes(config.getParam());    //写入流
                            }
                        };
                        Request request = addHeaders().url(config.getUrl()).post(requerstBody).build();
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
                        responseEntity.setStatus(FastHttp.result_net_err);
                    }
                }
                break;
                case GET:
                    StringBuilder tempParams = new StringBuilder();
                    try {
                        //处理参数
                        int pos = 0;
                        for (String key : config.getParams().keySet()) {
                            if (pos > 0) {
                                tempParams.append("&");
                            }
                            //对参数进行URLEncoder
                            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(String.valueOf(config.getParams().get(key)), "utf-8")));
                            pos++;
                        }
                        //补全请求地址
                        String requestUrl = String.format("%s?%s", config.getUrl(), tempParams.toString());
                        //创建一个请求
                        Request request = addHeaders().url(requestUrl).build();
                        //执行请求
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
                        responseEntity.setStatus(FastHttp.result_net_err);
                    }
                    break;
            }
            Log.i("httpApi", "拦截结果:" + responseEntity);
            return responseEntity;
        }
    };

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder();

        String ticket = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "ticket", 0);
        builder.addHeader(HeaderConst.MYMHOTEL_TICKET, ticket);
        builder.addHeader(HeaderConst.MYMHOTEL_TYPE, "1001");
        builder.addHeader(HeaderConst.MYMHOTEL_VERSION, "1");
        builder.addHeader(HeaderConst.MYMHOTEL_DATATYPE, "JSON");
        builder.addHeader(HeaderConst.MYMHOTEL_SOURCECODE, "");
        builder.addHeader(HeaderConst.MYMHOTEL_DATETIME, new Date().getTime() + "");
        builder.addHeader(HeaderConst.MYMHOTEL_ACKDATATYPE, "JSON");
        builder.addHeader("content-type", "application/json;charset=utf-8");
        return builder;
    }

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
