package com.jdjt.mangrove.login;

import android.support.v7.app.AppCompatActivity;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 修改手机号  验证手机号
 */
@InLayer(value = R.layout.mem_find_password,parent = R.id.center_common,isTitle = true)
public class ChangePhoneActivity extends AppCompatActivity {

    @Init
    public void init(){

    }
}
