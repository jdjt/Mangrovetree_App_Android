package com.jdjt.mangrove.login;

import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 个人资料
 */

@InLayer(value = R.layout.mem_pesonal_info,parent = R.id.center_common,isTitle = true)
public class PesonalInfoActivity extends CommonActivity {
    //标题栏
    Toolbar toolbar_actionbar;

    @InView(value = R.id.tv_personal_name)
    TextView tv_personal_name;   //姓名
    @InView(value = R.id.tv_personal_telphone)
    TextView tv_personal_telphone;  //手机号
    @InView(value = R.id.ll_personal_layout)
    LinearLayout ll_personal_layout;  //姓名layout
    @InView(value = R.id.ll_account_tel_layout)
    LinearLayout ll_account_tel_layout;  //手机号layout
    @InView(value = R.id.ll_account_password_layout)
    LinearLayout ll_account_password_layout;  //密码layout


    @Init
    public void init() {
    }
}
