package com.jdjt.mangrove.activity.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.Url;
import com.jdjt.mangrovetreelibray.ioc.annotation.InAll;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBean;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBinder;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InParam;
import com.jdjt.mangrovetreelibray.ioc.annotation.InVa;
import com.jdjt.mangrovetreelibray.ioc.annotation.InVaER;
import com.jdjt.mangrovetreelibray.ioc.annotation.InVaOK;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.login.AccountEntity;
import com.jdjt.mangrovetreelibray.ioc.plug.login.LoginConfig;
import com.jdjt.mangrovetreelibray.ioc.plug.login.PluginLogin;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;
import com.jdjt.mangrovetreelibray.ioc.validator.VaMobile;
import com.jdjt.mangrovetreelibray.ioc.validator.Validator;
import com.jdjt.mangrovetreelibray.ioc.validator.ValidatorCore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer(R.layout.login)
public class LoginFragment extends Fragment {
    //    @InAll
//    Views v;
    @InVa(value = VaMobile.class, empty = false, msg = "请输入正确的手机", index = 1)
    @InView
    EditText login_account;//账号
    @InVa(maxLength = 18, minLength = 6, msg = "请输入长度6-18位由字母数字_和-组成的密码", index = 2)
    @InView
    EditText login_password;//密码

    @InView(binder =  @InBinder(listener = OnClick.class, method = "click"))
    Button login_button;//登录按钮

    @InView(binder = @InBinder(listener = OnClick.class, method = "click") )
    Button login_findpwd_button;//忘记密码

    /**
     * 当点击登陆按钮，会自动获取输入框内的用户名和密码，对其进行验证
     */
    private void click(View view) {
        Validator.verify(getActivity());
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

//    /**
//     * 当点击登陆按钮，会自动获取输入框内的用户名和密码，对其进行验证
//     */
//    @Override
//    public void onValiResult(View view) {
//        if (view == null) {
//            //验证通过
//            MangrovetreeApplication.getInstance().http.u(this).login(login_account.getText().toString(), login_password.getText().toString());
//        } else {
//            //验证失败给出提示语
//            Toast.makeText(getContext(), "账号密码不能为空", Toast.LENGTH_SHORT).show();
//        }
//    }

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

    @InVaOK
    private void onValidationSucceeded() {
        Toast.makeText(getContext(), "验证成功", Toast.LENGTH_SHORT).show();
        MangrovetreeApplication.getInstance().http.u(this).login(login_account.getText().toString(), login_password.toString());
    }

    @InVaER
    public void onValidationFailed(ValidatorCore core) {
        if (TextView.class.isAssignableFrom(core.getView().getClass())) {
            EditText editText = core.getView();
            editText.requestFocus();
            editText.setFocusable(true);
            editText.setError(core.getMsg());
        }
    }


}
