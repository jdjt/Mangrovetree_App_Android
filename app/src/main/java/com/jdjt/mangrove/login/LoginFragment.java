package com.jdjt.mangrove.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.android.wrapmv.Tools;
import com.fengmap.drpeng.FMAPI;
import com.fengmap.drpeng.OutdoorMapActivity;
import com.google.gson.JsonObject;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBinder;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_TextStyle;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;
import com.jdjt.mangrovetreelibray.ioc.verification.Rule;
import com.jdjt.mangrovetreelibray.ioc.verification.Validator;
import com.jdjt.mangrovetreelibray.ioc.verification.Validator.ValidationListener;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.Password;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.Telphone;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer(R.layout.login)
public class LoginFragment extends Fragment implements ValidationListener {
    //    @InAll
//    Views v;
    @Telphone(empty = false, message = "请输入正确的手机号", order = 1)
    @InView
    EditText login_account;//账号

    @Password(maxLength = 18, minLength = 6, message = "请输入长度6-18位由字母数字_和-组成的密码", order = 2)
    @InView
    EditText login_password;//密码

    @InView(value = R.id.password_visible)
    CheckBox password_visible;//密码显示隐藏的checkbox

    @InView(binder = @InBinder(listener = OnClick.class, method = "click"))
    Button login_button;//登录按钮

    @InView(binder = @InBinder(listener = OnClick.class, method = "click"))
    TextView login_findpwd_button;//忘记密码
    Validator validator;



    /**
     * 当点击登陆按钮，会自动获取输入框内的用户名和密码，对其进行验证
     */
    private void click(View view) {

        switch (view.getId()){
            case R.id.login_findpwd_button:
                startActivity(new Intent(getActivity(),FindPasswordActivity.class));
                break;
            case R.id.login_button:
                //验证
                validator = new Validator(this);
                validator.setValidationListener(this);
                validator.validate();
                break;
        }
    }


    @Init
    public void init() {
        Ioc.getIoc().getLogger().i("初始化登录页面");
        String account = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "account", 0);
        String password = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "password", 0);

        login_account.setText(account);
        login_password.setText(password);
        password_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (password_visible.isChecked()) {
                    //密码显示
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码隐藏
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    @InHttp(Constant.HttpUrl.LOGIN_KEY)
    public void result(ResponseEntity entity) {

        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(getContext(), "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        if (entity.getContentAsString() == null || entity.getContentAsString().length() == 0) {
            Toast.makeText(getContext(), "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        //解析返回的数据
        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        //------------------------------------------------------------
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        Ioc.getIoc().getLogger().e("ticket:"+data.get("ticket"));
       if("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))){
           //存储用户名密码到本地
           Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "account", login_account.getText().toString());
           Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "password", login_password.getText().toString());
           Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "ticket",data.get("ticket"));
//           pDialog.dismiss();
           startActivity();
       }else {
           Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_LONG).show();
       }
        //------------------------------------------------------------
    }

    private void startActivity(){
        Bundle b = new Bundle();
        b.putString(FMAPI.ACTIVITY_WHERE, getActivity().getClass().getName());
        b.putString(FMAPI.ACTIVITY_MAP_ID, Tools.OUTSIDE_MAP_ID);
        FMAPI.instance().gotoActivity(getActivity(), OutdoorMapActivity.class, b);
        getActivity().finish();
        return;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if(pDialog!=null){
//            pDialog.dismiss();
//            pDialog=null;
//        }
    }

    @Override
    public void onValidationSucceeded() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("account", login_account.getText().toString());
        jsonObject.addProperty("password", login_password.getText().toString());
        MangrovetreeApplication.instance.http.u(this).login(jsonObject.toString());
//        showLoading();
    }
//    SweetAlertDialog   pDialog;
//    public void showLoading(){
//           pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("加载中...");
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();
        if (failedView instanceof EditText) {
            failedView.requestFocus();
            Handler_TextStyle handler_TextStyle = new Handler_TextStyle();
            handler_TextStyle.setString(message);
//            handler_TextStyle.setBackgroundColor(Color.RED, 0, message.length());
            ((EditText) failedView).setError(handler_TextStyle.getSpannableString());
        } else {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
