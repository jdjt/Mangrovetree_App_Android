package com.jdjt.mangrove.application;

import android.app.Application;

import com.fengmap.android.FMMapSDK;
import com.fengmap.drpeng.CrashHandler;
import com.fengmap.drpeng.common.ResourcesUtils;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrove.http.HttpInterFace;
import com.jdjt.mangrovetreelibray.ioc.net.IocHttpListener;
import com.jdjt.mangrovetreelibray.ioc.net.IocListener;
import com.jdjt.mangrovetreelibray.ioc.net.NetConfig;
import com.jdjt.mangrovetreelibray.ioc.plug.PlugConstants;
import com.jdjt.mangrovetreelibray.ioc.plug.login.LoginParameter;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.InternetConfig;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;
import com.jdjt.mangrovetreelibray.ioc.util.Http;

import java.io.File;
import java.util.HashMap;

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



    @Override
    public void onCreate() {
        instance = this;

        plugLoad();

        FMMapSDK.init(this, ResourcesUtils.getSDPath() + "/fm_drpeng");
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getAppExceptionHandler(this));

        super.onCreate();
    }

    // 增加一个自动获取照片的第三方库
    public void plugLoad() {
        // ------------------------------------------------------------------
        // 从本地或者摄像头获取照片模块
//        PlugConstants.setPlugIn(new PhotoParameter());
        // 登录模块
        PlugConstants.setPlugIn(new LoginParameter());
        // ------------------------------------------------------------------
        // 框架只是实现了分发，具体核心请求模块还必须用自己或者第三方网络请求框架
        // 网络请求的统一拦截处 异步请求 请返回 请求的值 异步 手动分发
        http = new Http<HttpInterFace>(HttpInterFace.class);
        IocListener.newInstance().setHttpListener(listener);
    }
    //全局拦截器
    public IocHttpListener<ResponseEntity> listener = new IocHttpListener<ResponseEntity>() {

        @Override
        public ResponseEntity netCore(NetConfig config) {
            System.out.println("拦截请求："+config);
            config.setHead(HeaderConst.inHeaders());
            InternetConfig netConfig = InternetConfig.defaultConfig();
//            netConfig.setHead();

            ResponseEntity reslut = null;
            switch (config.getType()) {
                case GET:
                    reslut = FastHttp.get(config.getUrl(), config.getParams(), netConfig);
                    break;
                case POST:
                    reslut = FastHttp.post(config.getUrl(), config.getParams(), netConfig);
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
            System.out.println("拦截结果："+reslut);
            return reslut;
        }
    };


}
