package com.jdjt.mangrove.login;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBinder;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
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

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer(R.layout.login)
public class LoginFragment extends Fragment implements ValidationListener {
    //    @InAll
//    Views v;
    @Telphone(empty = false, message = "请输入正确的手机号", order = 1)
    @InView()
    EditText login_account;//账号
    @Password(maxLength = 18, minLength = 6, message = "请输入长度6-18位由字母数字_和-组成的密码", order = 2)
    @InView
    EditText login_password;//密码

    @InView(binder =  @InBinder(listener = OnClick.class, method = "click"))
    Button login_button;//登录按钮

    @InView(binder = @InBinder(listener = OnClick.class, method = "click") )
    Button login_findpwd_button;//忘记密码
    Validator validator;
    /**
     * 当点击登陆按钮，会自动获取输入框内的用户名和密码，对其进行验证
     */
    private void click(View view) {
        //验证
        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.validate();
    }


    @Init
    public void init() {

        Ioc.getIoc().getLogger().e("初始化登录页面");
//        AccountEntity datas = getSave();
//        //是否保存用户名密码
//        System.out.println("是否已经保存了用户名密码："+datas.isSave());
//        //所有存储的用户名密码
//        System.out.println("所有存储的用户名密码："+datas.getAccountLists());
//        //存储的最新的一条用户名密码
//        if (datas.getLastAccount()!=null) {
//            System.out.println("存储的最新的一条账户是 "+datas.getLastAccount()+" 用户名是 "+datas.getLastAccount().get(AccountEntity.NAME));
//        }
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
        int status = Integer.valueOf(data.get("status").toString());
        if (status == 0) {
            Toast.makeText(getContext(), data.get("data").toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        //------------------------------------------------------------
        //清除保存的数据
//		clear("bbb");清除账号bbb的缓存
//		clear();清除所有缓存
    }


    @Override
    public void onValidationSucceeded() {
        Toast.makeText(getContext(), "验证成功"+login_password.getText(), Toast.LENGTH_SHORT).show();
        MangrovetreeApplication.instance.http.u(this).login(login_account.getText().toString(), login_password.getText().toString());
    }

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
