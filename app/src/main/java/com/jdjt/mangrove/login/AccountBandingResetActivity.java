package com.jdjt.mangrove.login;

import android.content.Intent;
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
@InLayer(value = R.layout.mem_accountbanding_reset, parent = R.id.center_common)
public class AccountBandingResetActivity extends CommonActivity implements Validator.ValidationListener {
    @Telphone(empty = false, message = "请输入正确的手机号", order = 1)
    @InView(value = R.id.banding_tel_phone)
    private EditText banding_tel_phone;//账号

    @TextRule(maxLength = 6, minLength = 6, message = "验证码不正确请重新输入", order = 2)
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
    String reuuid;
    String account;
    String code;//原手机验证码

    @Init
    private void init() {
        code = getIntent().getStringExtra("code");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取验证码60秒钟内的uuid,如果有则取,如果没有则重新生成
        if (MapVo.get("find_validation") != null) {
            uuid = MapVo.get("find_validation").toString();
        } else {
            uuid = Uuid.getUuid();//给初始值
        }
    }
    //    @InResume
//    private void resume() {
//
//    }

    @InListener(ids = {R.id.binding_validate, R.id.binding_submit}, listeners = OnClick.class)
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
            case R.id.binding_submit:
                //验证
                validator = new Validator(this);
                validator.setValidationListener(this);
                validator.validate();
                break;
            case R.id.binding_validate:
//                reuuid = Uuid.getUuid();//用于参数的uuid
//                MapVo.set("find_validation_re", reuuid);
                checkAccount();
                break;
        }
    }

    /**
     * 验证验证码
     */
    private void checkCode() {
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

    /**
     * 绑定手机
     */
    private void rebindPhone() {
        String account = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "account", 0);
        String password = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "password", 0);
        HashMap json = new HashMap();
        json.put("password", password);//原用户名
        json.put("bindingType", "1");//手机号
        json.put("oldBindingInfo", setBandingParams(account, code));
        json.put("newBindingInfo", setBandingParams(banding_tel_phone.getText().toString(), binding_security_code.getText().toString()));
//        json.put("code",code);
//        json.put("uuid",uuid);
        System.out.println("hyn" + new Gson().toJson(json));
        MangrovetreeApplication.instance.http.u(this).reBindingPhone(new Gson().toJson(json));
    }

    /**
     * targ 手机号
     * code 验证码
     *
     * @return
     */
    private HashMap setBandingParams(String targ, String code) {
        HashMap bandingMap = new HashMap<String, Object>();
        bandingMap.put("targ", targ);//手机号
        bandingMap.put("code", code);//验证码
        bandingMap.put("uuid", uuid);//客户端uuid
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

    @InHttp({Constant.HttpUrl.GETCODE_KEY, Constant.HttpUrl.REBINDINGPHONE_KEY, Constant.HttpUrl.CHECKACCOUNT_KEY})
    public void result(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }

        Ioc.getIoc().getLogger().e(entity.getContentAsString());
        //------------------------------------------------------------
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        //解析返回的数据
        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            switch (entity.getKey()) {
                case Constant.HttpUrl.GETCODE_KEY:

                    break;
//                case Constant.HttpUrl.REBINDINGPHONE_KEY:
//                    Toast.makeText(this, "成功了", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(this,PesonalInfoActivity.class));
//                    finish();
//                    break;
                case Constant.HttpUrl.CHECKACCOUNT_KEY:
                    String result = data.get("result") + "";
                    Ioc.getIoc().getLogger().e(result);
                    //账号重复
                    if (result.equals("1")) {
                        Ioc.getIoc().getLogger().e("该手机号已注册");
//                        CommonUtils.onErrorToast(register_account, "该账号已注册，请直接登录", getActivity());
                        Toast.makeText(this, "该账号已注册", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getCode();

                    break;
            }
        }
        else {//有错误
            String message= (String) heads.get(HeaderConst.MYMHOTEL_MESSAGE);
            String b=message.substring(message.length()-12,message.length());
            Toast.makeText(this, b, Toast.LENGTH_SHORT).show();
        }
    }
    @InHttp(Constant.HttpUrl.REBINDINGPHONE_KEY)
    public void result1(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            finish();
        } else {
            String message= (String) heads.get(HeaderConst.MYMHOTEL_MESSAGE);
            String b=message.substring(message.length()-12,message.length());
            Toast.makeText(this, b, Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onValidationSucceeded() {
//        showLoading();
        rebindPhone();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        CommonUtils.onErrorToast(failedView, failedRule.getFailureMessage(), this);
    }
}
