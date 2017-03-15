package com.jdjt.mangrove.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrove.util.MapVo;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InListener;
import com.jdjt.mangrovetreelibray.ioc.annotation.InResume;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;

import java.util.Map;

/**
 * Created by huyanan on 2017/3/13.
 * 设置
 */
@InLayer(value = R.layout.mem_setting, parent = R.id.center_common, isTitle = true)
public class SettingActivity extends CommonActivity {

    @InView(value = R.id.account_logout)
    Button account_logout;

    @Init
    public void init() {
//        account_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });

    }

        @InListener(ids = {R.id.account_logout},listeners = OnClick.class)
    private void click(View view){
        switch (view.getId()){
            case R.id.account_logout:
                logout();
                break;
        }
    }
    private void logout() {
        MangrovetreeApplication.instance.http.u(this).logout();
    }
//
//    @InResume
//    private void getUser() {
//        JsonObject json = new JsonObject();
//        json.addProperty("proceedsPhone", "");
//        MangrovetreeApplication.instance.http.u(this).getUserInfo(json.toString());
//    }

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
}