package com.jdjt.mangrove.util;

import android.content.Context;
import android.content.Intent;

import com.jdjt.mangrove.service.DownloadService;

/**
 * @author wmy
 * @Description: 公共类 杂项方法可以写在这里做整理
 * @FileName:CommonUtils
 * @Package com.jdjt.mangrove.util
 * @Date 2017/3/27 16:23
 */
public class CommonUtils {

    public static void updateApp(Context context) {
        //启动服务
        Intent service = new Intent(context,DownloadService.class);
        context.startService(service);
    }
}
