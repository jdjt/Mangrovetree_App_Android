package com.jdjt.mangrove.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.drpeng.OutdoorMapActivity;
import com.google.gson.JsonObject;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrove.login.PesonalInfoActivity;
import com.jdjt.mangrove.login.SettingActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBack;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBinder;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InUI;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wmy
 * @Description:
 * @FileName:LeftFragment
 * @Package com.jdjt.mangrove.fragment
 * @Date 2017/3/13 15:43
 */
@InLayer(R.layout.header_nav)
public class LeftFragment extends Fragment {

    @InView(value = R.id.account_islogin, binder = @InBinder(listener = OnClick.class, method = "click"))
    private  LinearLayout account_islogin; //用户中心
    @InView(value = R.id.ll_account_setting_layout, binder = @InBinder(listener = OnClick.class, method = "click"))
    private LinearLayout ll_account_setting_layout;//设置
    @InView
    private  TextView account_item_name;
    private void click(View view) {
        ((OutdoorMapActivity) getActivity()).isShow();
        Intent intent=new Intent(getActivity(), PesonalInfoActivity.class);
        switch (view.getId()) {
//                //跳转到用户中心
            case R.id.account_islogin:
                intent.putExtra("title","个人中心");
                startActivity(intent);
                break;
            //跳转到设置
            case R.id.ll_account_setting_layout:
                intent=new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("title","设置");
                startActivity(intent);

                break;
        }

    }
    @Init
    @InBack
    public void init() {
        // get global application bus
        JsonObject json=new JsonObject();
        json.addProperty("proceedsPhone","");
        MangrovetreeApplication.instance.http.u(this).getUserInfo(json.toString());
    }
    @InHttp(Constant.HttpUrl.GETUSERINFO_KEY)
    public void result(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(getActivity(), "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        if (entity.getContentAsString() == null || entity.getContentAsString().length() == 0) {
            Toast.makeText(getActivity(), "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        //解析返回的数据
        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        //------------------------------------------------------------
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))){
            //存储用户名密码到本地
            Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "nickname", data.get("nickname"));
            Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "callPhone",data.get("callPhone"));

            updateUi(data.get("callPhone")+"");
        }
        //------------------------------------------------------------
    }

    @InUI
    private void updateUi(String name){
        account_item_name.setText(name);
    }
}
