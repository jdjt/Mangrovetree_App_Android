package com.jdjt.mangrove.activity.login;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.activity.base.SysBaseAppCompatActivity;

/**
 * Created by huyanan on 2017/3/9.
 */

public class LonginAndRegisterFragmentActivity extends SysBaseAppCompatActivity {
    private RadioButton login_button;
    private RadioButton register_button;
    private LoginFragment loginFragment;
    private RegisterPhoneFragment registerPhoneFragment;


    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_longin_and_register_fragment;
    }

    @Override
    protected void initView() {
        initViews();
        initEvents();
        select(0);
    }

    //初始化View
    private void initViews() {
        login_button = (RadioButton) findViewById(R.id.login_button);
        register_button = (RadioButton) findViewById(R.id.register_button);
    }

    //初始化 监听事件
    private void initEvents() {
        login_button.setOnClickListener((View.OnClickListener) this);
        register_button.setOnClickListener((View.OnClickListener) this);
    }

    private void select(int i) {
        FragmentManager fm = getFragmentManager();  //获得Fragment管理器
        FragmentTransaction ft = fm.beginTransaction(); //开启一个事务

        switch (i) {
            case 0:
//                             login_button.setBackgroundResource(R.drawable.chatting_biaoqing_btn_enable);
                if (loginFragment == null) {
                    loginFragment = new LoginFragment();
                    ft.add(R.id.login_register_fragment, registerPhoneFragment);
                } else {
                    ft.show(loginFragment);
                }
                break;
            case 1:
//                             register_button.setBackgroundResource(R.drawable.lbs_icon_enable);
                if (registerPhoneFragment == null) {
                    registerPhoneFragment = new RegisterPhoneFragment();
                    ft.add(R.id.login_register_fragment, registerPhoneFragment);
                } else {
                    ft.show(registerPhoneFragment);
                }
                break;
        }
        ft.commit();   //提交事务
    }

//    //隐藏所有Fragment
//    private void hidtFragment(FragmentTransaction fragmentTransaction) {
//        if (loginFragment != null) {
//            fragmentTransaction.hide(loginFragment);
//        }
//        if (registerPhoneFragment != null) {
//            fragmentTransaction.hide(registerPhoneFragment);
//        }
//    }

    //重写监听
    public void onClick(View v) {
//                 initImageBack(); //初始化 图片背景
        switch (v.getId()) {
            case R.id.login_button:
                select(0);
                break;
            case R.id.register_button:
                select(1);
                break;
        }
    }

}
