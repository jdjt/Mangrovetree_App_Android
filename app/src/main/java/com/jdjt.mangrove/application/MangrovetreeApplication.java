package com.jdjt.mangrove.application;

import android.app.Application;

import com.fengmap.android.FMMapSDK;
import com.fengmap.drpeng.CrashHandler;
import com.fengmap.drpeng.common.ResourcesUtils;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:MangrovetreeApplication
 * @Package com.jdjt.mangrovetree.application
 * @Date 2017/3/9 11:30
 */
public class MangrovetreeApplication extends Application {

    @Override
    public void onCreate() {

        FMMapSDK.init(this, ResourcesUtils.getSDPath() + "/fm_drpeng");
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getAppExceptionHandler(this));

        super.onCreate();
    }
}
