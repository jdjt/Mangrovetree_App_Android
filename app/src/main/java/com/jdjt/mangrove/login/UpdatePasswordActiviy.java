package com.jdjt.mangrove.login;

import android.support.v7.app.AppCompatActivity;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 修改登录密码
 */
@InLayer(value = R.layout.mem_update_password,parent = R.id.center_common,isTitle = true)
public class UpdatePasswordActiviy extends AppCompatActivity {

    @Init
    public void init(){

    }
}
