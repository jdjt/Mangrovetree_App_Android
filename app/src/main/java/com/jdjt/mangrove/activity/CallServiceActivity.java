package com.jdjt.mangrove.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.android.map.geometry.FMTotalMapCoord;
import com.fengmap.android.wrapmv.entity.FMZone;
import com.fengmap.android.wrapmv.service.FMLocationService;
import com.fengmap.drpeng.FMAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrove.entity.MapInfo;
import com.jdjt.mangrove.view.ClearEditText;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InListener;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_System;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Time;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Ui;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@InLayer(value = R.layout.activity_call_service, parent = R.id.center_common)
public class CallServiceActivity extends CommonActivity {
    @InView
    private LinearLayout layout_msg_content;//发送消息体
    @InView
    private EditText edit_msg_content;
    @InView
    private Button btn_send;
    @InView
    private TextView text_msg_region; //区域
    @InView
    private TextView text_msg_content;//消息内容
    @InView
    private TextView text_msg_date;//发送时间
    @InView
    private TextView text_task_timer;//任务计时
    FMTotalMapCoord locatePosition;
    MenuItem item;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        SpannableString ss = new SpannableString("取消呼叫");//定义hint的值
//        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(32,false);//设置字体大小 true表示单位是sp
//        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
        menu.add(Menu.NONE, 1, 0, addColor("取消呼叫", R.drawable.text_color_enable)).setVisible(false).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item = menu.getItem(0);
//        getMenuInflater().inflate(R.menu.mangrove_main, menu);
//        menu.getItem(0).setEnabled(false);
        return true;
    }

    /*
        * Add color to a given text
        */
    private SpannableStringBuilder addColor(CharSequence text, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (color != 0) {
            builder.setSpan(new ForegroundColorSpan(color), 0, text.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(CallServiceActivity.this, "取消呼叫", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Init
    private void init() {
        SpannableString ss = new SpannableString(getString(R.string.edit_hint_lable));//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(14, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edit_msg_content.setHint(new SpannedString(ss));
        edit_msg_content.clearFocus();

        layout_msg_content.setVisibility(View.GONE);
    }

    @InListener(ids = {R.id.layout_msg_content, R.id.btn_send}, listeners = {OnClick.class})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.layout_msg_content:
                Handler_Ui.hideSoftKeyboard(edit_msg_content);
                break;
            case R.id.btn_send:

                sendData();
                break;
        }

    }

    FMZone fmZone = null;

    private void getLocationPosition() {
//        FMLocationService.instance().start();
//        if (FMLocationService.instance().isRunning()) {
//            locatePosition = getdefaultcoord();
//            fmZone = FMAPI.instance().mZoneManager.getCurrentZone(locatePosition);
//        }
        locatePosition = getdefaultcoord();
        fmZone = FMAPI.instance().mZoneManager.getCurrentZone(locatePosition);
    }

    /**
     * 初始化发送数据
     */
    private void sendData() {
        if (TextUtils.isEmpty(edit_msg_content.getText())) return;
//        getLocationPosition();

//        FMZone fmZone = FMAPI.instance().mZoneManager.getCurrentZone(locatePosition);
        text_msg_content.setText(edit_msg_content.getText());
        text_msg_date.setText(Handler_Time.getInstance().getFormatStr(""));
        if (fmZone != null) {
            text_msg_region.setText(getString(R.string.text_call_region, fmZone.getZoneName()));
        } else
            text_msg_region.setText(getString(R.string.text_call_region, ""));
        btn_send.setEnabled(false);
        addTask();
    }

    private void clear(){
        btn_send.setEnabled(true);
        layout_msg_content.setVisibility(View.GONE);

    }
    /**
     * 穿件任务请求
     */
    private void addTask() {
        showLoading();
        try {
            HashMap json = new HashMap();
            json.put("customerId","1008");
            json.put("taskContent",text_msg_content.getText()+"");
            json.put("mapInfo",getMapInfo());
            String jsonStr=new Gson().toJson(json);
            Ioc.getIoc().getLogger().e(jsonStr);
            MangrovetreeApplication.instance.http.u(this).addTask(jsonStr);
        }catch (Exception e){
            e.printStackTrace();
            dismissDialog();
            clear();
            Toast.makeText(this, "请求错误", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 获取地图信息
     * @return
     */
    private HashMap getMapInfo() {
        HashMap map = new HashMap();
        map.put("hotelCode", "");
        map.put("floorNo", "");
        map.put("mapNo", "");
        map.put("posionX", "");
        map.put("positionY", "");
        map.put("postionZ", "");
        return map;
    }

    @InHttp({Constant.HttpUrl.ADDTASK_KEY})
    public void result(ResponseEntity entity) {
        dismissDialog();
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            btn_send.setEnabled(true);
            return;
        }
        if (entity.getContentAsString() == null || entity.getContentAsString().length() == 0) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            btn_send.setEnabled(true);
            return;
        }
        HashMap<String, Object> data=null;
        //解析返回的数据
        if(!TextUtils.isEmpty(entity.getContentAsString()))
            data = Handler_Json.JsonToCollection(entity.getContentAsString());
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            switch (entity.getKey()) {
                case Constant.HttpUrl.ADDTASK_KEY:
                    layout_msg_content.setVisibility(View.VISIBLE);
                    btn_send.setEnabled(false);
                    edit_msg_content.setText("");
                    return;
            }
        }else {
            Toast.makeText(this, data.get("errMessage")+"", Toast.LENGTH_SHORT).show();
            btn_send.setEnabled(true);
        }
    }
}

