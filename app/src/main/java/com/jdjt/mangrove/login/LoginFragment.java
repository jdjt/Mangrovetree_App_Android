package com.jdjt.mangrove.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.jdjt.mangrovetreelibray.ioc.annotation.NotProguard;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
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

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer(R.layout.login)
@NotProguard
public class LoginFragment extends Fragment implements ValidationListener {
    //    @InAll
//    Views v;
    @NotProguard
    @Telphone(empty = false, message = "请输入正确的手机号", order = 1)
    @InView
    EditText login_account;//账号
    @NotProguard
    @Password(maxLength = 18, minLength = 6, message = "请输入长度6-18位由字母数字_和-组成的密码", order = 2)
    @InView
    EditText login_password;//密码
    @NotProguard
    @InView(value = R.id.password_visible)
    CheckBox password_visible;//密码显示隐藏的checkbox
    @NotProguard
    @InView(binder = @InBinder(listener = OnClick.class, method = "click"))
    Button login_button;//登录按钮
    @NotProguard
    @InView(binder = @InBinder(listener = OnClick.class, method = "click"))
    TextView login_findpwd_button;//忘记密码
    @NotProguard
    Validator validator;


    /**
     * 当点击登陆按钮，会自动获取输入框内的用户名和密码，对其进行验证
     */
    public void click(View view) {

        switch (view.getId()) {
            case R.id.login_findpwd_button:
                startActivity(new Intent(getActivity(), FindPasswordActivity.class));
                break;
            case R.id.login_button:
                //验证
                validator = new Validator(this);
                validator.setValidationListener(this);
                validator.validate();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Init
    @NotProguard
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


    @InHttp({Constant.HttpUrl.LOGIN_KEY, Constant.HttpUrl.CHECKACCOUNT_KEY})
    public void result(ResponseEntity entity) {
        Toast.makeText(getActivity(), "进入了返回结果", Toast.LENGTH_SHORT).show();
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
        Map<String, Object> heads = entity.getHeaders();
        switch (entity.getKey()) {
            case Constant.HttpUrl.LOGIN_KEY:
                Log.i("LoginApi","ticket:" + data.get("ticket"));
                Log.i("LoginApi","登录验证中。。。。" );
                //------------------------------------------------------------
                //判断当前请求返回是否 有错误，OK 和 ERR
                Ioc.getIoc().getLogger().e("ticket:" + data.get("ticket"));
                if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
                    Log.i("httpApi","登录验证成功" );
                    //存储用户名密码到本地
                    Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "account", login_account.getText().toString());
                    Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "password", login_password.getText().toString());
                    Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "ticket", data.get("ticket"));
                    startActivity();
                    return;
                } else {
                    Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_LONG).show();
                }
                return;
            case Constant.HttpUrl.CHECKACCOUNT_KEY:
                Log.i("LoginApi","帐号验证中...." );
                //判断当前请求返回是否 有错误，OK 和 ERR
                if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
                    if ("0".equals(data.get("result"))) {
                        Toast.makeText(getActivity(), "该账户未注册,请先注册", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Log.i("LoginApi","验证成功" );
                    login();

                } else {
                    Toast.makeText(getActivity(), "网络请求失败，请稍后再试", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    private void startActivity() {
        Bundle b = new Bundle();
        b.putString(FMAPI.ACTIVITY_WHERE, getActivity().getClass().getName());
        b.putString(FMAPI.ACTIVITY_MAP_ID, Tools.OUTSIDE_MAP_ID);
        Intent intent = new Intent(getActivity(), OutdoorMapActivity.class);
        intent.putExtras(b);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
        return;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onValidationSucceeded() {
        checkAccount();
    }

    /**
     * 登陆
     */
    private void login() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("account", login_account.getText().toString());
        jsonObject.addProperty("password", login_password.getText().toString());
        MangrovetreeApplication.instance.http.u(this).login(jsonObject.toString());
    }

    /**
     * 验证账号是否存在
     */
    private void checkAccount() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("account", login_account.getText().toString());
        MangrovetreeApplication.instance.http.u(this).checkAccount(jsonObject.toString());
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
