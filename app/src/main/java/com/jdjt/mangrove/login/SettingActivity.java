package com.jdjt.mangrove.login;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fengmap.android.data.FMDataManager;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrove.util.MapVo;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InListener;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_File;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_System;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by huyanan on 2017/3/13.
 * 设置
 */
@InLayer(value = R.layout.mem_setting, parent = R.id.center_common, isTitle = true)
public class SettingActivity extends CommonActivity {

    @InView(value = R.id.account_logout)
    Button account_logout;
    @InView(value = R.id.app_cache)
    TextView app_cache;
    @InView(value = R.id.about_mangrove)
    TextView about_mangrove;
    @Init
    public void init() {
//        try {
//         String TotalCache=getTotalCacheSize(this);
//            app_cache.setText(app_cache.getText()+"  "+ TotalCache);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @InListener(ids = {R.id.account_logout,R.id.app_cache,R.id.about_mangrove}, listeners = OnClick.class)
    private void click(View view) {
        switch (view.getId()) {
            case R.id.account_logout:
                logout();
                break;
            case R.id.app_cache:
                show();
                break;
            case R.id.about_mangrove:
                startActivity(new Intent(this,AboutMangroveActivity.class));
                break;
        }
    }


    private void show(){
        showConfirm("是否确定清除本地数据？", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Handler_System.cleanApplicationData(getApplicationContext(),"");
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }
    private void logout() {
        MangrovetreeApplication.instance.http.u(this).logout();
    }

    @InHttp(Constant.HttpUrl.LOGOUT_KEY)
    public void result1(ResponseEntity entity) {
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            //退出成功
            Handler_SharedPreferences.removeSharedPreferences(Constant.HttpUrl.DATA_USER, "password");
            MapVo.map = null;
            Intent it = new Intent(this, LoginAndRegisterFragmentActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
            finish();
        }
    }
    /**
     * 获取当前应的缓存文件大小
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = Handler_System.getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += Handler_System.getFolderSize(context.getExternalCacheDir());
        }
        cacheSize += Handler_System.getFolderSize(Handler_File.getCacheDirF(FMDataManager.getCacheDirectory()));
        return Handler_System.getFormatSize(cacheSize);
    }


}