package com.jdjt.mangrove.util;

import android.content.Context;
import android.content.Intent;

import com.jdjt.mangrove.service.DownloadService;
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

    public static void updateApp(Context context) {
        //启动服务
        Intent service = new Intent(context,DownloadService.class);
        context.startService(service);
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
}
