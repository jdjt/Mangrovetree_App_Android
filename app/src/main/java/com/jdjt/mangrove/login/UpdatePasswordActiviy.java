package com.jdjt.mangrove.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

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
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;
import com.jdjt.mangrovetreelibray.ioc.util.CommonUtils;
import com.jdjt.mangrovetreelibray.ioc.verification.Rule;
import com.jdjt.mangrovetreelibray.ioc.verification.Validator;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.ConfirmPassword;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.Password;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.TextRule;

import java.util.Map;

/**
 * Created by huyanan on 2017/3/13.
 * 修改登录密码
 */
@InLayer(value = R.layout.mem_update_password, parent = R.id.center_common, isTitle = true)
public class UpdatePasswordActiviy extends CommonActivity implements Validator.ValidationListener {
    @TextRule(maxLength = 18, minLength = 6, message = "请输入6-18位的原密码", order = 1)
    @InView
    private EditText password_old;

    @Password(maxLength = 18, minLength = 6, message = "请输入长度6-18位由字母数字_和-组成的密码", order = 2)
    @InView
    private EditText password_new;
    @ConfirmPassword(messageResId = R.string.err, order = 3)
    @InView
    private EditText password_new_confirm;
    //验证
    Validator validator;

    @Init
    public void init() {

    }

    @InListener(ids = R.id.password_submit_btn, listeners = OnClick.class)
    private void click(View v) {
        //验证
        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.validate();
    }

    private void updatePwd() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password_old.getText().toString());
        jsonObject.addProperty("newPassword", password_new.getText().toString());
        MangrovetreeApplication.instance.http.u(this).modifyPassword(jsonObject.toString());
    }

    @Override
    public void onValidationSucceeded() {
        showLoading();
        updatePwd();
    }

    @InHttp(Constant.HttpUrl.MODIFYPASSWORD_KEY)
    public void result(ResponseEntity entity) {
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

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        CommonUtils.onErrorToast(failedView, failedRule.getFailureMessage(), this);
    }
}
