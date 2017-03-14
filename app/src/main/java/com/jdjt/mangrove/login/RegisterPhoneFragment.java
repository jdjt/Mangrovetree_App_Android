package com.jdjt.mangrove.login;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.verification.Rule;
import com.jdjt.mangrovetreelibray.ioc.verification.Validator.ValidationListener;
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
import java.util.Timer;
import java.util.TimerTask;

import static com.jdjt.mangrovetreelibray.ioc.ioc.IocFragmentHandler.handler;

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer(R.layout.register)
public class RegisterPhoneFragment extends Fragment implements ValidationListener {

    private int time = 60;//初始秒
    Timer timer = new Timer();

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
    Button register_button;  //注册按钮
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
//        timer.schedule(task, 1000, 1000);

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
                    register_button.setBackgroundResource(R.color.unPresentMonth_FontColor);

                }
            }
        });

    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }

//    final Handler handler = new Handler(){    
//        @Override    
//        public void handleMessage(Message msg){    
//            switch (msg.what) {    
//            case 1:    
//                txtView.setText(""+recLen);    
//                if(recLen < 0){    
//                    timer.cancel();    
//                    txtView.setVisibility(View.GONE);    
//                }    
//            }    
//        }    
//    };  
//    TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//            time--;
//            Message message = new Message();
//            message.what = 1;
//            handler.sendMessage(message);
//        }
//    };

}
