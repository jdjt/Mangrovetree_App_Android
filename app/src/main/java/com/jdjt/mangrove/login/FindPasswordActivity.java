package com.jdjt.mangrove.login;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 忘记密码（验证手机号）
 */
@InLayer(value = R.layout.mem_find_password,parent = R.id.center_common,isTitle = true)
public class FindPasswordActivity extends AppCompatActivity {
//    @InView(value = R.id.toolbar_actionbar);
        Toolbar toolbar_actionbar;



@Init
    public void init(){
    toolbar_actionbar.findViewById(R.id.toolbar_actionbar);
    toolbar_actionbar.setTitle("重置密码");
}

}
