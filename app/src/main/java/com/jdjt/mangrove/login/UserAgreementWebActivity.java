package com.jdjt.mangrove.login;

import android.content.Intent;
import android.webkit.WebView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by hyn on 2017/3/16.
 */

@InLayer(value = R.layout.wv_user_agreement,parent = R.id.center_common,isTitle = true)
public class UserAgreementWebActivity extends CommonActivity {

    @InView(R.id.web_view)
    WebView web_view;
    @Init
    public void init(){
        Intent intent = this.getIntent();

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.loadUrl("http://www.mymhotel.com/jhpt/app/v1/xieyi2017a.html");
    }
}
