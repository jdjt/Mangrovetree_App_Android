package com.jdjt.mangrove.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.Telphone;
import com.jdjt.mangrovetreelibray.ioc.verification.annotation.TextRule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyanan on 2017/3/13.
 * 修改手机号  验证手机号
 */
@InLayer(value = R.layout.mem_find_password,parent = R.id.center_common,isTitle = true)
public class ChangePhoneActivity extends CommonActivity implements Validator.ValidationListener{
    @Telphone(empty = false, message = "请输入正确的手机号", order = 1)
    @InView(value = R.id.find_account)
    private EditText find_account;//账号

    @TextRule(maxLength = 6, minLength = 6, message = "验证码不正确请重新输入", order = 2)
    @InView(value = R.id.find_security_code)
    private EditText find_security_code;//验证码

    @InView(value = R.id.find_validation)
    private Button find_validation;//验证码按钮
    @InView(value = R.id.find_next_button)
    private Button find_next_button;  //下一步按钮
    //验证
    Validator validator;

    //计时
    CountTimer mc;
    String uuid;
    String account;
    String callPhone;
    @InResume
    private void resume() {
        //获取验证码60秒钟内的uuid,如果有则取,如果没有则重新生成
        if (MapVo.get("find_validation") != null) {
            uuid = MapVo.get("find_validation").toString();
        } else {
            uuid = Uuid.getUuid();//给初始值
        }
        callPhone = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "callPhone", 0);
        find_account.setText(callPhone);
        find_account.setFocusable(false);
    }

    @InListener(ids = {R.id.find_validation, R.id.find_next_button}, listeners = OnClick.class)
    private void click(View view) {

        account = callPhone;
        Ioc.getIoc().getLogger().e("当前注册手机号：" + account);
        //验证账号 邮箱,手机
        if (!account.matches(Rules.REGEX_TELPHONE)) {
            CommonUtils.onErrorToast(find_account, "请输入正确的手机号", this);
            return;
        }
        //验证手机是否联网
        if (!Handler_Network.isNetworkAvailable(this)) {
            Toast.makeText(this, "手机未联网", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.find_next_button:
                //验证
                validator = new Validator(this);
                validator.setValidationListener(this);
                validator.validate();
                break;
            case R.id.find_validation:
                uuid = Uuid.getUuid();//用于参数的uuid
                MapVo.set("find_validation", uuid);
                getCode();
                break;
        }
    }

    /**
     * 验证验证码
     */
    private  void checkCode(){

        JsonObject json = new JsonObject();
        json.addProperty("code", find_security_code.getText().toString());
        json.addProperty("uuid", uuid);
        MangrovetreeApplication.instance.http.u(this).checkCaptcha(json.toString());

    }
    /**
     * 获取手机验证码
     */
    private void getCode() {
        JsonObject json = new JsonObject();
        json.addProperty("account", account);
        json.addProperty("logicFlag", "1");
        json.addProperty("uuid", uuid);
        MangrovetreeApplication.instance.http.u(this).getCode(json.toString());
        mc = new CountTimer(60000, 1000, find_validation, "find_validation");
        mc.start();
    }
    @InHttp({Constant.HttpUrl.GETCODE_KEY,Constant.HttpUrl.CHECKCAPTCHA_KEY,Constant.HttpUrl.CHECKACCOUNT_KEY})
    public void result(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        //解析返回的数据
        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        //解析返回的数据
        Ioc.getIoc().getLogger().e(entity.getContentAsString());
        //------------------------------------------------------------
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            switch (entity.getKey()) {
                case Constant.HttpUrl.GETCODE_KEY:

                    break;
                case Constant.HttpUrl.CHECKCAPTCHA_KEY:

                    String r = data.get("result") + "";
                    if (r.equals("1")) {
                        Toast.makeText(this, "验证码不正确请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(new Intent(this,AccountBandingResetActivity.class).putExtra("code",find_security_code.getText().toString()));
                    this.finish();
                    break;
                case Constant.HttpUrl.CHECKACCOUNT_KEY: //验证账号重复性，如果不重复 则发送验证码
                    String result = data.get("result") + "";
                    Ioc.getIoc().getLogger().e(result);
                    //账号重复
                    if (result.equals("1")) {
                        Ioc.getIoc().getLogger().e( "该手机号已注册");
                        CommonUtils.onErrorToast(find_account, "该账号已注册", this);
                        return;
                    }
                    break;
            }
        }
    }
    /**
     * 验证手机是否重复
     */
    private void checkAccount() {
        JsonObject json = new JsonObject();
        String account = find_account.getText() + "";
        json.addProperty("account", account);
        MangrovetreeApplication.instance.http.u(this).checkAccount(json.toString());
    }

    @Override
    public void onValidationSucceeded() {
        checkCode();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        CommonUtils.onErrorToast(failedView, failedRule.getFailureMessage(), this);
    }
}
