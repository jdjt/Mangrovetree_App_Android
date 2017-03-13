package com.jdjt.mangrove.login;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 个人资料
 */

@InLayer(value = R.layout.mem_pesonal_info,parent = R.id.center_common,isTitle = true)
public class PesonalInfoActivity extends AppCompatActivity {
    //标题栏
    Toolbar toolbar_actionbar;

    @InView(value = R.id.personal_name)
    TextView personal_name;   //姓名
    @InView(value = R.id.personal_telphone)
    TextView personal_telphone;  //手机号
    @InView(value = R.id.personal_layout)
    LinearLayout personal_layout;  //姓名layout
    @InView(value = R.id.account_tel_layout)
    LinearLayout account_tel_layout;  //手机号layout
    @InView(value = R.id.account_password_layout)
    LinearLayout account_password_layout;  //密码layout


    @Init
    public void init() {
        toolbar_actionbar.findViewById(R.id.toolbar_actionbar);
        toolbar_actionbar.setTitle("用户中心");
    }
}
