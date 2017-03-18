package com.jdjt.mangrove.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.activity.MapSearchAcitivity;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

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
        final String map = data.get("title");
        Ioc.getIoc().getLogger().e(map);
        TextView txt = holder.getView(R.id.tags_name);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,0,screenWidth/28,0);
        layoutParams.width=(screenWidth-(screenWidth/28))/4;
        txt.setWidth(screenWidth/5);
        txt.setSingleLine(true);
        txt.setGravity(Gravity.CENTER);
        txt.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        txt.setTextSize(12l);
//        txt.setPadding(screenWidth/28,screenWidth/28,screenWidth/28,screenWidth/28);

//        txt.setLayoutParams(layoutParams);
        txt.setText(map);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapSearchAcitivity acitivity= (MapSearchAcitivity) getContext();
                acitivity.search(map);
            }
        });

    }

//    @Override
//    protected void bindViewHolder(BaseViewHolder holder, int position, String data) {
//        TextView txt = holder.getView(R.id.tags_name);
//        txt.setText(data+"");
//    }
}