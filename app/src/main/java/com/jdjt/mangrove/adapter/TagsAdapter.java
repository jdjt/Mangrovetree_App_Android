package com.jdjt.mangrove.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_System;

import java.util.Map;

/**
 * @author wmy
 * @Description:
 * @FileName:TagsAdapter
 * @Package com.jdjt.exchange.adapter
 * @Date 2016/9/8 17:46
 */
public class TagsAdapter extends AppBaseAdapter<Map<String, String>, AppBaseAdapter.BaseViewHolder> {
    int screenHeight;
    int screenWidth;

    public TagsAdapter(Context context) {
        super(context);
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        return new BaseViewHolder(View.inflate(getContext(), R.layout.layout_tags_item, null));
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position, Map<String, String> data) {
        String map = data.get("title");

        TextView txt = holder.getView(R.id.tags_name);
        txt.setWidth(screenWidth/5- Handler_System.dip2px(4));
        txt.setGravity(Gravity.CENTER);
        txt.setText(map);

    }

//    @Override
//    protected void bindViewHolder(BaseViewHolder holder, int position, String data) {
//        TextView txt = holder.getView(R.id.tags_name);
//        txt.setText(data+"");
//    }
}