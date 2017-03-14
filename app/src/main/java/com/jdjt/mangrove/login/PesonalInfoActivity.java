package com.jdjt.mangrove.login;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyanan on 2017/3/13.
 * 个人资料
 */

@InLayer(value = R.layout.mem_pesonal_info,parent = R.id.center_common,isTitle = true)
public class PesonalInfoActivity extends CommonActivity {


    @InView(value = R.id.tv_personal_name)
    TextView tv_personal_name;   //姓名
    @InView(value = R.id.tv_personal_telphone)
    TextView tv_personal_telphone;  //手机号
    @InView(value = R.id.ll_personal_layout)
    LinearLayout ll_personal_layout;  //姓名layout
    @InView(value = R.id.ll_account_tel_layout)
    LinearLayout ll_account_tel_layout;  //手机号layout
    @InView(value = R.id.ll_account_password_layout)
    LinearLayout ll_account_password_layout;  //密码layout


    @Init
    public void init() {
        //获取本地数据
        String nickname = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "nickname", 0);
        String callPhone = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "callPhone", 0);
        tv_personal_name.setText(nickname);
        tv_personal_telphone.setText(callPhone);
        //同时更新网络数据，如果有变更 更新到本地

    }

    @InListener(ids = {R.id.ll_personal_layout,R.id.ll_account_tel_layout,R.id.ll_account_password_layout,R.id.account_logout},listeners = OnClick.class)
    private void click(View view){
        switch (view.getId()){
            case R.id.ll_personal_layout:
                startActivity(new Intent(PesonalInfoActivity.this,ChangeNameActivity.class).putExtra("name",tv_personal_name.getText()));
                break;
            case R.id.ll_account_tel_layout:
                startActivity(new Intent(PesonalInfoActivity.this,ChangePhoneActivity.class));
                break;
            case R.id.ll_account_password_layout:
                startActivity(new Intent(PesonalInfoActivity.this,UpdatePasswordActiviy.class));
                break;
            case R.id.account_logout:
                logout();
                break;
        }
    }

    private  void logout(){
        MangrovetreeApplication.instance.http.u(this).logout();
    }
    /**
     * 每次启动的时候更新一次数据
     */
    @InResume
    private void getUser(){
        JsonObject json=new JsonObject();
        json.addProperty("proceedsPhone","");
        MangrovetreeApplication.instance.http.u(this).getUserInfo(json.toString());
    }
    @InHttp(Constant.HttpUrl.LOGOUT_KEY)
    public void result1(ResponseEntity entity) {
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            //退出成功
            Handler_SharedPreferences.removeSharedPreferences(Constant.HttpUrl.DATA_USER, "password");
            MapVo.map = null;
            Intent it = new Intent(this, LoginAndRegisterFragmentActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
            finish();
        }

    }
    @InHttp({Constant.HttpUrl.GETUSERINFO_KEY})
    public void result(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        if (entity.getContentAsString() == null || entity.getContentAsString().length() == 0) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
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
            tv_personal_name.setText(data.get("nickname")+"");
            tv_personal_telphone.setText(data.get("callPhone")+"");
        }
        //------------------------------------------------------------
    }
}
