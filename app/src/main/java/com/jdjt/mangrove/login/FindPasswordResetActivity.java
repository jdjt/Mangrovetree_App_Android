package com.jdjt.mangrove.login;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InListener;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;
import com.jdjt.mangrovetreelibray.ioc.util.CommonUtils;
import com.jdjt.mangrovetreelibray.ioc.verification.Rule;
import com.jdjt.mangrovetreelibray.ioc.verification.Validator;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.ConfirmPassword;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.Password;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.TextRule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyanan on 2017/3/13.
 * 忘记密码后重置登录密码
 */
@InLayer(value = R.layout.mem_find__password_reset,parent = R.id.center_common,isTitle = true)
public class FindPasswordResetActivity extends CommonActivity implements Validator.ValidationListener {
    @Password(maxLength = 18, minLength = 6, message = "请输入长度6-18位由字母数字组成的密码", order = 1)
    @InView
    private EditText find_reset_password;

    @ConfirmPassword(messageResId = R.string.err, order = 2)
    @InView
    private EditText find_reset_password_cm;
    //验证
    Validator validator;

    @InListener(ids = R.id.find_reset_button, listeners = OnClick.class)
    private void click(View v) {
        //验证
        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.validate();
    }
    @Override
    public void onValidationSucceeded() {
        setFindResetPassword();
    }
    String account,code,uuid;
    @Init
    private  void init(){
        account=getIntent().getStringExtra("account");
        code=getIntent().getStringExtra("code");
        uuid=getIntent().getStringExtra("uuid");
    }
    /**
     * 获取手机验证码
     */
    private void setFindResetPassword() {
        JsonObject json = new JsonObject();
        json.addProperty("account", account);
        json.addProperty("code", code);
        json.addProperty("password",find_reset_password.getText().toString());
        json.addProperty("uuid", uuid);
        MangrovetreeApplication.instance.http.u(this).resetPassword(json.toString());
    }
    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        CommonUtils.onErrorToast(failedView, failedRule.getFailureMessage(), this);
    }
    @InHttp({Constant.HttpUrl.RESETPASSWORD_KEY})
    public void result(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        //解析返回的数据
//        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        Ioc.getIoc().getLogger().e(entity.getContentAsString());
        //------------------------------------------------------------
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            finish();
        }else {
            Toast.makeText(this,"错误",Toast.LENGTH_SHORT).show();
        }
    }

}
