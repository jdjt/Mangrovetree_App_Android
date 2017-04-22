package com.jdjt.mangrove.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengmap.android.FMDevice;
import com.fengmap.android.wrapmv.Tools;
import com.fengmap.drpeng.FMAPI;
import com.fengmap.drpeng.IndoorMapActivity;
import com.fengmap.drpeng.OutdoorMapActivity;
import com.fengmap.drpeng.db.FMDBMapElementOveridDao;
import com.fengmap.drpeng.widget.ToastUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.adapter.SearchListAdapter;
import com.jdjt.mangrove.adapter.TabFragmentAdapter;
import com.jdjt.mangrove.adapter.TagsAdapter;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.entity.Stores;
import com.jdjt.mangrove.fragment.SearchFragment;
import com.jdjt.mangrove.view.FlowLayout;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_System;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InLayer(value = R.layout.bind_room_acitivity, parent = R.id.center_common)
public class BindRoomAcitivity extends CommonActivity{
    @InView
    Button bind_button;
    @InView
    EditText room_number_etv;
    @InView
    EditText idcard_number_etv;
    @Init
    private void initView() {
        setActionBarTitle("客房绑定");
        bind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用绑房接口
                ToastUtils.showToast(BindRoomAcitivity.this,"调用绑房接口");
            }
        });
    }

}
