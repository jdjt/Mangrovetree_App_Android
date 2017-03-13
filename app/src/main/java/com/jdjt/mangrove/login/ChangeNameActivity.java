package com.jdjt.mangrove.login;

import android.support.v7.app.AppCompatActivity;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 修改姓名
 */
@InLayer(value = R.layout.change_name,parent = R.id.center_common,isTitle = true)
public class ChangeNameActivity extends CommonActivity {

    @Init
    public void init(){

    }
}
