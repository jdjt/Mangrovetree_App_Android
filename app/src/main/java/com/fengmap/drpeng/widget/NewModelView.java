package com.fengmap.drpeng.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fengmap.android.FMMapSDK;
import com.fengmap.android.wrapmv.Tools;
import com.fengmap.android.wrapmv.entity.FMExternalModelRelation;
import com.fengmap.drpeng.FMAPI;
import com.fengmap.drpeng.IndoorMapActivity;
import com.fengmap.drpeng.OutdoorMapActivity;
import com.jdjt.mangrove.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;

/**
 * @author rjh
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:NewModelView
 * @Package com.fengmap.drpeng.widget
 * @Date 2017/3/13 16:22
 */
public class NewModelView extends RelativeLayout {
    public  Context    mContext;
    private ExpandableListView mExpandableListView;
    private View view;
    private boolean isExpand = true;
    private RelativeLayout panel;
    private LinearLayout navi_layout;
    private RelativeLayout navi_layout_x,navi_layout_xx;
    private ScrollView scroll;
    private Handler mHanler;
    TextView combo_name, group_open_icon, combo_details;
    TextView fm_navi_start, fm_navi_end;
    TextView fm_open_navi_small,fm_enter_inside,fm_open_navi_big_x;
    TextView fm_navi_need_distance,fm_navi_need_time,fm_navi_need_calorie;
    TextView fm_navi_need_distance_x,fm_navi_need_time_x,fm_navi_need_calorie_x;
    ImageView combo_image;
    private String mEnterMapId;
    private View divider_line;

    private HashMap<String,String> group = new HashMap<>();
    private HashMap<String,String> child = new HashMap<>();

    public NewModelView(Context context) {
        super(context);
        this.mContext = context;
        isExpand = true;
        mHanler  = new Handler(Looper.getMainLooper());
        initView();
    }

    public NewModelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext= context;
        initView();
    }

    private void initView() {
        view = View.inflate(mContext, R.layout.new_view_model_info,this);
        combo_name = (TextView) view.findViewById(R.id.combo_name);
        combo_details = (TextView) view.findViewById(R.id.combo_details);
        group_open_icon = (TextView) view.findViewById(R.id.group_open_icon);
        fm_enter_inside = (TextView) view.findViewById(R.id.fm_enter_inside);
        fm_open_navi_small = (TextView) view.findViewById(R.id.fm_open_navi_small);
        fm_open_navi_big_x = (TextView) view.findViewById(R.id.fm_open_navi_big_x);
        fm_navi_start = (TextView) view.findViewById(R.id.fm_navi_start);
        fm_navi_end = (TextView) view.findViewById(R.id.fm_navi_end);
        divider_line = view.findViewById(R.id.divider_line);

        fm_navi_need_distance = (TextView) view.findViewById(R.id.fm_navi_need_distance);
        fm_navi_need_time = (TextView) view.findViewById(R.id.fm_navi_need_time);
        fm_navi_need_calorie = (TextView) view.findViewById(R.id.fm_navi_need_calorie);

        fm_navi_need_distance_x = (TextView) view.findViewById(R.id.fm_navi_need_distance_x);
        fm_navi_need_time_x = (TextView) view.findViewById(R.id.fm_navi_need_time_x);
        fm_navi_need_calorie_x = (TextView) view.findViewById(R.id.fm_navi_need_calorie_x);

        combo_image= (ImageView) view.findViewById(R.id.combo_image);
        // 进入室内点击逻辑
        fm_enter_inside.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入室内界面
                if (mEnterMapId==null || mEnterMapId.equals("")) {
                    CustomToast.show(mContext, "没有室内地图");
                    return;
                }
                Activity a = (Activity)mContext;
                Bundle b = new Bundle();
                if (a instanceof OutdoorMapActivity) {
                    b.putString(FMAPI.ACTIVITY_WHERE, OutdoorMapActivity.class.getName());
                    b.putString(FMAPI.ACTIVITY_MAP_ID, mEnterMapId);
                    b.putString(FMAPI.ACTIVITY_HOTEL_NAME, Tools.getInsideMapName(mEnterMapId));
                    Log.d("TAGTAGTAG","Tools.getInsideMapName(mEnterMapId) = "+Tools.getInsideMapName(mEnterMapId)+" mEnterMapId="+mEnterMapId);
                }
                FMAPI.instance().gotoActivity(a, IndoorMapActivity.class, b);
            }
        });
        panel = (RelativeLayout) view.findViewById(R.id.panel);
        navi_layout = (LinearLayout) view.findViewById(R.id.navi_layout);
        navi_layout_x = (RelativeLayout) view.findViewById(R.id.navi_layout_x);
        navi_layout_xx = (RelativeLayout) view.findViewById(R.id.navi_layout_xx);
        panel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpand){
                    mHanler.post(new Runnable() {
                        @Override
                        public void run() {
                            navi_layout.setVisibility(View.GONE);
                            divider_line.setVisibility(GONE);
                            group_open_icon.setBackgroundResource(R.mipmap.arrow_up);
                        }
                    });
                    isExpand = false;
                }else {
                    mHanler.post(new Runnable() {
                        @Override
                        public void run() {
                            navi_layout.setVisibility(View.VISIBLE);
                            divider_line.setVisibility(VISIBLE);
                            group_open_icon.setBackgroundResource(R.mipmap.arrow_down);
                        }
                    });
                    isExpand = true;
                }
            }
        });
    }


    /**
     * 设置标题。
     * @param pTitle
     */
    public void setTitle(String pTitle) {
        if (pTitle.equals("") || pTitle == null) {
            combo_name.setText("暂无名称");
        } else {
            combo_name.setText(pTitle);
        }
    }

    /**
     * 通过点击到的模型的id, 查询进入室内地图的ID
     * @param pModelFid
     */
    public void setEnterMapIdByModelFid(String pModelFid) {
        FMExternalModelRelation emr= FMMapSDK.getExternalModelRelations().get(pModelFid);
        if (emr == null) {   // 没有室内
            setEnterViewVisible(false);
            return;
        } else {
            setEnterViewVisible(true);
        }
        isExpand = true;
        navi_layout.setVisibility(View.VISIBLE);
        group_open_icon.setBackgroundResource(R.mipmap.arrow_down);
        mEnterMapId = emr.getMapId();
    }

    /**
     * 设置是否进入室内外的View
     *
     */
    public void setEnterViewVisible(boolean pVisible) {
        if (pVisible) {
            navi_layout_x.setVisibility(GONE);
            navi_layout_xx.setVisibility(VISIBLE);
        } else {
            navi_layout_x.setVisibility(VISIBLE);
            navi_layout_xx.setVisibility(GONE);
        }
    }
    /**
    * @method 设置导航起始点
    */
    public void setStartText(String startName) {
        fm_navi_start.setText(startName);
        fm_navi_start.setTextSize(14);
    }
    /**
     * @method 设置导航起终点
     */
    public void setEndText(String endName) {
        if (endName.equals("") || endName == null) {
            endName = "未知位置";
        }
        fm_navi_end.setText(endName);
        fm_navi_end.setTextSize(14);
    }

    /**
    * @method 开始导航按钮
    */
    public TextView getSmallArriveButton() {
        return fm_open_navi_small;
    }

    public TextView getBigArriveButton() {
        return fm_open_navi_big_x;
    }

    public void setNaviNeedTime(String needTime) {
        fm_navi_need_time.setText("步行"+needTime);
        fm_navi_need_time_x.setText("步行"+needTime);
    }

    public void setNaviNeedDistance(String needDistance) {
        fm_navi_need_distance.setText(needDistance);
        fm_navi_need_distance_x.setText(needDistance);
    }

    public void setNaviNeedCalorie(String needCalorie) {
        fm_navi_need_calorie.setText("燃烧"+needCalorie);
        fm_navi_need_calorie_x.setText("燃烧"+needCalorie);
    }

    public void setComboName(String name) {
        combo_name.setText(name);
    }

    public void setComboDetails(String details) {
        combo_details.setText(details);
    }

    //是否显示详情
    public void showDetail(boolean isShow){
        if(isShow){
            panel.setVisibility(VISIBLE);
        }else {
            panel.setVisibility(GONE);
        }
    }
    public void downloadImage(ImageLoader imageLoader,String url){
        imageLoader.displayImage(url,combo_image);
    }
}
