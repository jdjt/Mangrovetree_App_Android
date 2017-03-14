package com.jdjt.mangrove.login;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.view.ClearEditText;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * Created by huyanan on 2017/3/13.
 * 修改姓名
 */
@InLayer(value = R.layout.change_name,parent = R.id.center_common,isTitle = true)
public class ChangeNameActivity extends CommonActivity {


    @InView
    private ClearEditText edit_person_name;
    private Drawable dRight;
    private Rect rBounds;
    @Init
    public void init(){
    }
}
