package com.jdjt.mangrove.login;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InVa;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.plug.net.InternetConfig;
import com.jdjt.mangrovetreelibray.ioc.util.CountTimer;
import com.jdjt.mangrovetreelibray.ioc.validator.VaMobile;
import com.jdjt.mangrovetreelibray.ioc.validator.VaPassword;
import com.jdjt.mangrovetreelibray.ioc.validator.Validator;

import java.util.Map;

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer( R.layout.register)
public class RegisterPhoneFragment extends Fragment {


    @InVa(value = VaMobile.class, empty = false, msg = "请输入正确的手机号", index = 1)
    @InView(value = R.id.register_account)
    EditText register_account;//账号

    @InVa(value = VaPassword.class, maxLength = 18, minLength = 6, msg = "请输入长度6-18位由字母数字_和-组成的密码", index = 3)
    @InView(value = R.id.register_password)
    EditText register_password;//密码

    @InVa(maxLength = 6, minLength = 1, msg = "输入验证码", index = 2)
    @InView(value = R.id.register_security_code)
    EditText register_security_code;//验证码

    @InView(value = R.id.register_valitation)
    Button register_valitation;//验证码按钮
    //	@InView(value=R.id.register_email_btn)
//    Button register_email_btn;//邮箱注册
    @InView(value = R.id.read_agreement)
    CheckBox read_agreement;//同意条款
    @InView(value = R.id.register_button)
            Button register_button;
    Boolean agree_flag = true;
    //验证
    Validator validator;

    //计时
    CountTimer mc;

    Map<String, String> regMap;

    InternetConfig inConfig;

    String uuid;

    @Init
    public void init() {
        Ioc.getIoc().getLogger().e("初始化注册页面");
    }
}
