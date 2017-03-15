package com.jdjt.mangrove.login;

import android.view.View;

import com.google.gson.JsonObject;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrove.view.ClearEditText;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InListener;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;

import java.util.Map;

/**
 * Created by huyanan on 2017/3/13.
 * 修改姓名
 */
@InLayer(value = R.layout.change_name,parent = R.id.center_common,isTitle = true)
public class ChangeNameActivity extends CommonActivity {
    @InView
    private ClearEditText edit_person_name;

    @Init
    private void init(){
        edit_person_name.setText(getIntent().getStringExtra("name"));
    }
    @InListener(ids = {R.id.btn_submit},listeners = OnClick.class)
    private void click(View view){
        modifyMember();
    }

    private void modifyMember(){
        JsonObject json=new JsonObject();
        json.addProperty("nickname",edit_person_name.getText().toString());
        json.addProperty("sex","");
        MangrovetreeApplication.instance.http.u(this).modifyMember(json.toString());
    }
    @InHttp({Constant.HttpUrl.MODIFYMEMBER_KEY})
    public void result(ResponseEntity entity) {
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            finish();
        }
    }
}
