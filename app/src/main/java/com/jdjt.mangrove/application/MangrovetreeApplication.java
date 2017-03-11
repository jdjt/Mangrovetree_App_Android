package com.jdjt.mangrove.application;

import android.app.Application;
import android.content.Context;

import com.fengmap.android.FMMapSDK;
import com.fengmap.drpeng.CrashHandler;
import com.fengmap.drpeng.common.ResourcesUtils;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

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
    private static Context context;

    public synchronized static MangrovetreeApplication getInstance() {
        if (null == instance) {
            instance = new MangrovetreeApplication();
        }
        return instance;
    }

    public static Context getAppContext() {
        return MangrovetreeApplication.context;
    }

    @Override
    public void onCreate() {

        MangrovetreeApplication.context = getApplicationContext();
        FMMapSDK.init(this, ResourcesUtils.getSDPath() + "/fm_drpeng");
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getAppExceptionHandler(this));

        super.onCreate();
    }
}
