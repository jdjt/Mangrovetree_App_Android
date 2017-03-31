package com.jdjt.mangrove.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
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
 * 更换手机号
 */
@InLayer(value = R.layout.mem_accountbanding_reset,parent = R.id.center_common)
public class AccountBandingResetActivity extends CommonActivity implements Validator.ValidationListener {
    @Telphone(empty = false, message = "请输入正确的手机号", order = 1)
    @InView(value = R.id.banding_tel_phone)
    private EditText banding_tel_phone;//账号

    @TextRule(maxLength = 6, minLength = 4, message = "验证码不正确请重新输入", order = 2)
    @InView(value = R.id.binding_security_code)
    private EditText binding_security_code;//验证码

    @InView(value = R.id.binding_validate)
    private Button binding_validate;//验证码按钮
    @InView(value = R.id.binding_submit)
    private Button binding_submit;  //保存按钮
    //验证
    Validator validator;

    //计时
    CountTimer mc;
    String uuid;
    String account;
    String code;//原手机验证码
    @Init
    private void init(){
        code=getIntent().getStringExtra("code");

    }
    @InResume
    private void resume() {
        //获取验证码60秒钟内的uuid,如果有则取,如果没有则重新生成
        if (MapVo.get("find_validation") != null) {
            uuid = MapVo.get("find_validation").toString();
        } else {
            uuid = Uuid.getUuid();//给初始值
        }
    }

    @InListener(ids = {R.id.find_validation, R.id.find_next_button}, listeners = OnClick.class)
    private void click(View view) {
        account = banding_tel_phone.getText().toString();
        Ioc.getIoc().getLogger().e("当前注册手机号：" + account);
        //验证账号 邮箱,手机
        if (!account.matches(Rules.REGEX_TELPHONE)) {
            CommonUtils.onErrorToast(banding_tel_phone, "请输入正确的手机号", this);
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
                checkAccount();
                break;
        }
    }

    /**
     * 验证验证码
     */
    private  void checkCode(){

        JsonObject json = new JsonObject();
        json.addProperty("code", binding_security_code.getText().toString());
        json.addProperty("uuid", uuid);
        MangrovetreeApplication.instance.http.u(this).checkCaptcha(json.toString());

    }

    /**
     * 登陆密码	password	String	Y	 Base64加密
     绑定区分	bindingType	int	N	1:手机号、2：邮箱
     原绑定对象信息	oldBindingInfo	Object	Y	原绑定的信息,绑定时为空即不穿此信息。

     1	绑定对象	targ	String	N	原手机号或邮箱
     2	验证码	code	String	N	原手机收到的验证码
     3	唯一限制标识	uuid	String	N	客户端生成，原手机或邮箱获取验证码时所使用的标识。
     新绑定对象信息	newBindingInfo	Object	N	新绑定的信息
     1	绑定对象	targ	String	N	新的手机号或邮箱
     2	验证码	code	String	N	新手机收到的验证码
     3	唯一限制标识	uuid	String	N	客户端生成，新手机或邮箱获取验证码时所使用的标识。

     */
    private void rebindPhone(){
        String account = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "account", 0);
        String password = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "password", 0);
        JsonObject json = new JsonObject();
        json.addProperty("password",password);
        json.addProperty("bindingType", "1");
        json.addProperty("oldBindingInfo", new Gson().toJson(setBandingParams(account,code)));
        json.addProperty("newBindingInfo", new Gson().toJson(setBandingParams(banding_tel_phone.getText().toString(),binding_security_code.getText().toString())));
        MangrovetreeApplication.instance.http.u(this).reBindingPhone(json.toString());
    }

    /**
     * targ 手机号
     * code 验证码
     * @return
     */
    private HashMap setBandingParams(String targ,String code){
        HashMap  bandingMap  = new HashMap<String, Object>();
        bandingMap.put("targ",targ);//手机号
        bandingMap.put("code",code);//验证码
        bandingMap.put("uuid", Uuid.getUuid());//客户端uuid
        return bandingMap;
    }
    /**
     * 验证手机是否重复
     */
    private void checkAccount() {
        JsonObject json = new JsonObject();
        String account = banding_tel_phone.getText() + "";
        json.addProperty("account", account);
        MangrovetreeApplication.instance.http.u(this).checkAccount(json.toString());
    }

    /**
     * 获取手机验证码
     */
    private void getCode() {
        JsonObject json = new JsonObject();
        String account = banding_tel_phone.getText() + "";
        json.addProperty("account", account);
        json.addProperty("logicFlag", "1");
        json.addProperty("uuid", uuid);
        MangrovetreeApplication.instance.http.u(this).getCode(json.toString());
        mc = new CountTimer(60000, 1000, binding_validate, "find_validation");
        mc.start();
    }
    @InHttp({Constant.HttpUrl.GETCODE_KEY,Constant.HttpUrl.REBINDINGPHONE_KEY,Constant.HttpUrl.CHECKACCOUNT_KEY})
    public void result(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        //解析返回的数据
        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        Ioc.getIoc().getLogger().e(entity.getContentAsString());
        //------------------------------------------------------------
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            switch (entity.getKey()) {
                case Constant.HttpUrl.GETCODE_KEY:

                    break;
                case Constant.HttpUrl.REBINDINGPHONE_KEY:
                    finish();
                    break;
                case Constant.HttpUrl.CHECKACCOUNT_KEY:
                    getCode();

                    break;
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        showLoading();
        rebindPhone();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        CommonUtils.onErrorToast(failedView, failedRule.getFailureMessage(), this);
    }
}
