package com.jdjt.mangrove.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.fengmap.android.wrapmv.Tools;
import com.fengmap.drpeng.FMAPI;
import com.fengmap.drpeng.OutdoorMapActivity;
import com.google.gson.JsonObject;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
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
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Network;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;
import com.jdjt.mangrovetreelibray.ioc.util.CommonUtils;
import com.jdjt.mangrovetreelibray.ioc.util.CountTimer;
import com.jdjt.mangrovetreelibray.ioc.util.Uuid;
import com.jdjt.mangrovetreelibray.ioc.verification.Rule;
import com.jdjt.mangrovetreelibray.ioc.verification.Rules;
import com.jdjt.mangrovetreelibray.ioc.verification.Validator;
import com.jdjt.mangrovetreelibray.ioc.verification.Validator.ValidationListener;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.Password;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.Telphone;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.TextRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer(R.layout.register)
public class RegisterPhoneFragment extends Fragment implements ValidationListener {

    private int time = 60;//初始秒
    Timer timer = new Timer();

    @Telphone(empty = false, message = "请输入正确的手机号", order = 1)
    @InView(value = R.id.register_account)
    private EditText register_account;//账号

    @Password(maxLength = 18, minLength = 6, message = "请输入长度6-18位由字母数字组成的密码", order = 3)
    @InView(value = R.id.register_password)
    private EditText register_password;//密码

    @TextRule(maxLength = 6, minLength = 6, message = "验证码不正确请重新输入", order = 2)
    @InView(value = R.id.register_security_code)
    private EditText register_security_code;//验证码

    @InView(value = R.id.register_valitation)
    private Button register_valitation;//验证码按钮
    @InView(value = R.id.read_agreement)
    private CheckBox read_agreement;//同意条款
    @InView(value = R.id.register_button)
    private Button register_button;  //注册按钮

    @InView(value = R.id.register_agree_text)
    Button register_agree_text;
    //验证
    Validator validator;

    //计时
    CountTimer mc;
    String uuid;
    String account;

    private void resume() {
        Ioc.getIoc().getLogger().e(MapVo.get("register_valitation"));
        //获取验证码60秒钟内的uuid,如果有则取,如果没有则重新生成
        if (MapVo.get("register_valitation") != null) {

            uuid = MapVo.get("register_valitation").toString();

        } else {
            uuid = Uuid.getUuid();//给初始值
        }
        Ioc.getIoc().getLogger().e(uuid);
    }

    @Init
    public void init() {
        Ioc.getIoc().getLogger().e("初始化注册页面");
        resume();
        register_button.setEnabled(false);
        read_agreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (read_agreement.isChecked()) {
                    //选中可注册
                    register_button.setEnabled(true);
                    register_button.setBackgroundResource(R.drawable.setbtn_bg);
                } else {
                    //未选中不可注册
                    register_button.setEnabled(false);
                    register_button.setBackgroundResource(R.drawable.setbtn_bg_false);

                }
            }
        });
        register_agree_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserAgreementWebActivity.class));
            }
        });

    }

    @InListener(ids = {R.id.register_button, R.id.register_valitation}, listeners = OnClick.class)
    private void click(View view) {
        account = register_account.getText().toString();
        Ioc.getIoc().getLogger().e("当前注册手机号：" + account);
        //验证账号 邮箱,手机
        if (!account.matches(Rules.REGEX_TELPHONE)) {
            CommonUtils.onErrorToast(register_account, "请输入正确的手机号", getActivity());
            return;
        }
        //验证手机是否联网
        if (!Handler_Network.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "手机未联网", Toast.LENGTH_SHORT).show();
            return;
        }
        checkAccount();
        switch (view.getId()) {
            case R.id.register_button:
                //验证
                validator = new Validator(this);
                validator.setValidationListener(this);
                validator.validate();
                break;
            case R.id.register_valitation:
                getCode();

                break;
        }
    }


    /**
     * 验证手机是否重复
     */
    private void checkAccount() {
        JsonObject json = new JsonObject();
        String account = register_account.getText() + "";
        json.addProperty("account", account);
        MangrovetreeApplication.instance.http.u(this).checkAccount(json.toString());
    }

    /**
     * 获取手机验证码
     */
    private void getCode() {
        JsonObject json = new JsonObject();
        String account = register_account.getText() + "";
        json.addProperty("account", account);
        json.addProperty("logicFlag", "1");
        json.addProperty("uuid", uuid);
        MangrovetreeApplication.instance.http.u(this).getCode(json.toString());
        mc = new CountTimer(60000, 1000, register_valitation, "register_valitation");
        mc.start();
    }

    /**
     * 注册方法
     */
    private void register() {
        JsonObject json = new JsonObject();
        String account = register_account.getText().toString();
        json.addProperty("account", account);
        json.addProperty("code", register_security_code.getText().toString());
        json.addProperty("password", register_password.getText().toString());
        json.addProperty("uuid", uuid);
        MangrovetreeApplication.instance.http.u(this).register(json.toString());
    }

    /**
     * 登录方法
     */
    private void login() {
        Ioc.getIoc().getLogger().e("登录接口");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("account", register_account.getText().toString());
        jsonObject.addProperty("password", register_password.getText().toString());
        MangrovetreeApplication.instance.http.u(this).login(jsonObject.toString());
    }

    @InHttp({Constant.HttpUrl.GETCODE_KEY, Constant.HttpUrl.CHECKACCOUNT_KEY, Constant.HttpUrl.LOGIN_KEY, Constant.HttpUrl.CHECKCAPTCHA_KEY})
    public void result(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(getActivity(), "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        //解析返回的数据
        if (TextUtils.isEmpty(entity.getContentAsString())) {
            return;
        }
        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        Ioc.getIoc().getLogger().e(entity.getContentAsString());
        //------------------------------------------------------------
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {

            switch (entity.getKey()) {
                case Constant.HttpUrl.GETCODE_KEY:

                    break;
                case Constant.HttpUrl.CHECKACCOUNT_KEY: //验证账号重复性，如果不重复 则发送验证码
                    String result = data.get("result") + "";
                    Ioc.getIoc().getLogger().e(result);
                    //账号重复
                    if (result.equals("1")) {
                        Ioc.getIoc().getLogger().e("该手机号已注册");
                        Toast.makeText(getContext(), "该账号已注册，请直接登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    getCode();
                    break;
                case Constant.HttpUrl.CHECKCAPTCHA_KEY:
                    String r = data.get("result") + "";
                    if (r.equals("1")) {
                        Toast.makeText(getContext(), "验证码不正确请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    break;
                case Constant.HttpUrl.LOGIN_KEY:
                    if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
                        //存储用户名密码到本地
                        Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "account", register_account.getText().toString());
                        Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "password", register_password.getText().toString());
                        Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "ticket", data.get("ticket"));
                        startActivity();
                    }
                    break;
            }
        }
        //------------------------------------------------------------
    }

    @InHttp(Constant.HttpUrl.REGISTER_KEY)
    public void result1(ResponseEntity entity) {

        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(getActivity(), "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            MapVo.remove("register_valitation");
            login();
        } else {
            String message = (String) heads.get(HeaderConst.MYMHOTEL_MESSAGE);
            String b = message.substring(message.length() - 12, message.length());
            Toast.makeText(getContext(), b, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 跳转到首页
     */
    private void startActivity() {
        Bundle b = new Bundle();
        b.putString(FMAPI.ACTIVITY_WHERE, getActivity().getClass().getName());
        b.putString(FMAPI.ACTIVITY_MAP_ID, Tools.OUTSIDE_MAP_ID);
        FMAPI.instance().gotoActivity(getActivity(), OutdoorMapActivity.class, b);
        getActivity().finish();
        return;
    }

    @Override
    public void onValidationSucceeded() {
        register();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        CommonUtils.onErrorToast(failedView, failedRule.getFailureMessage(), getActivity());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
