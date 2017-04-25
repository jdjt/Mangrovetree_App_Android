package com.jdjt.mangrove.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengmap.android.FMDevice;
import com.fengmap.android.wrapmv.Tools;
import com.fengmap.drpeng.FMAPI;
import com.fengmap.drpeng.IndoorMapActivity;
import com.fengmap.drpeng.OutdoorMapActivity;
import com.fengmap.drpeng.db.FMDBMapElementOveridDao;
import com.fengmap.drpeng.widget.ToastUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.JsonObject;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.adapter.SearchListAdapter;
import com.jdjt.mangrove.adapter.TabFragmentAdapter;
import com.jdjt.mangrove.adapter.TagsAdapter;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.entity.Stores;
import com.jdjt.mangrove.fragment.SearchFragment;
import com.jdjt.mangrove.view.FlowLayout;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_System;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InLayer(value = R.layout.bind_room_acitivity, parent = R.id.center_common)
public class BindRoomAcitivity extends CommonActivity{
    @InView
    Button bind_button;
    @InView
    EditText room_number_etv;
    @InView
    EditText idcard_number_etv;
    @Init
    private void initView() {
        setActionBarTitle("客房绑定");
        bind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用绑房接口
//                ToastUtils.showToast(BindRoomAcitivity.this,"调用绑房接口");
                customerBindingRoom(room_number_etv.getText().toString(),idcard_number_etv.getText().toString());
            }
        });
    }


    /**
     *  获取绑房信息
     *  eg:"hotelCode":"2","roomCode":"61112","pagersCode":"4017"
     */
    private void customerBindingRoom(String roomCode,String pagersCode){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hotelCode", "2");
        jsonObject.addProperty("roomCode", roomCode);
        jsonObject.addProperty("pagersCode", pagersCode);
        jsonObject.addProperty("deviceId", FMDevice.getMacAddress());
        jsonObject.addProperty("deviceToken", getDeviceToken());
        jsonObject.addProperty("deviceType", "1");
        MangrovetreeApplication.instance.http.u(this).customerBindingRoom(jsonObject.toString());
        Log.d("NETNETNET", "网络请求参数：" + Constant.HttpUrl.CUSTOMER_BINDINGROOM+"  "+jsonObject.toString());
    }

    /**
     * 网络请求逻辑
     */
    @InHttp({Constant.HttpUrl.CUSTOMER_BINDINGROOM_KEY})
    public void result(ResponseEntity entity) {
        Log.d("NETNETNET", "网络请求的数据：" + entity.getContentAsString());
        //请求失败
        if (entity.getStatus() == FastHttp.result_net_err) {
            ToastUtils.showToast(this, "网络请求失败，请检查网络");
            return;
        }
        //解析返回的数据
        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        String retOk = data.get("retOk").toString();
        if(retOk!=null&&"0".equals(retOk)){
            //绑房成功 跳发任务界面
        }else {
            //跳绑房界面
        }
    }

}
