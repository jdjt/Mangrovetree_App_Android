package com.jdjt.mangrove.login;

import android.support.v7.app.AppCompatActivity;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 设置
 */
@InLayer(value = R.layout.mem_setting,parent = R.id.center_common,isTitle = true)
public class SettingActivity extends AppCompatActivity {

    @Init
    public void init(){

    }
}
