package com.jdjt.mangrove.login;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 设置
 */
@InLayer(value = R.layout.mem_setting,parent = R.id.center_common,isTitle = true)
public class SettingActivity extends CommonActivity {

    @Init
    public void init(){
    }
}
