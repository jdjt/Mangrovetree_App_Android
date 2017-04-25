package com.jdjt.mangrove.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_System;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;


/**
 * @author wmy
 * @Description: 公共类 杂项方法可以写在这里做整理
 * @FileName:CommonUtils
 * @Package com.jdjt.mangrove.util
 * @Date 2017/3/27 16:23
 */
public class CommonUtils implements Serializable {

    public static void startupdateApp(Context context,String url) {
        Downloader downloader=new Downloader(context);
        downloader.startDownload(url);
        downloader.setOnDownloadProgressChangeListener(new Downloader.OnDownloadProgressChangeListener() {
            @Override
            public void onDownloadProgressChange(long downloadSizeSoFar, long totalSize) {
                Log.i("CommonUtils","downloadSizeSoFar ="+downloadSizeSoFar);
                Log.i("CommonUtils","totalSize ="+totalSize);
            }
        });
    }


    /**
     * 获取Assets下的文件资源
     *
     * @param fileName 文件名称
     * @return String
     */
    public static String getFromAssets(Context context ,String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";

            while ((line = bufReader.readLine()) != null)
                Result += line;

        } catch (Exception e) {
            Ioc.getIoc().getLogger().e(e);
        }
        return Result;
    }
//    public static void  main(String[] args){
//        Ioc.getIoc().getLogger().e(Handler_System.getDeviceId(MangrovetreeApplication.instance));
//    }
}
