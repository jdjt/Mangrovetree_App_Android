package com.fengmap.drpeng.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    private LinearLayout content;
    private ScrollView scroll;
    private Handler mHanler;
    TextView combo_name, group_open_icon, combo_details;
    TextView fm_navi_need_distance, fm_navi_start, fm_navi_end;
    TextView fm_open_navi_small,fm_enter_inside,fm_open_navi_big;

    private String mEnterMapId;

    private HashMap<String,String> group = new HashMap<>();
    private HashMap<String,String> child = new HashMap<>();

    public NewModelView(Context context) {
        super(context);
        this.mContext = context;
        isExpand = true;
        mHanler  = new Handler(Looper.getMainLooper());
        initData();
        initView();
    }

    public NewModelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext= context;
        initData();
        initView();
    }

    private void initView() {
        view = View.inflate(mContext, R.layout.new_view_model_info,this);
        combo_name = (TextView) view.findViewById(R.id.combo_name);
        combo_details = (TextView) view.findViewById(R.id.combo_details);
        group_open_icon = (TextView) view.findViewById(R.id.group_open_icon);
        fm_enter_inside = (TextView) findViewById(R.id.fm_enter_inside);
        fm_open_navi_small = (TextView) findViewById(R.id.fm_open_navi_small);
        fm_open_navi_big = (TextView) findViewById(R.id.fm_open_navi_big);
        fm_navi_start = (TextView) findViewById(R.id.fm_navi_start);
        fm_navi_end = (TextView) findViewById(R.id.fm_navi_end);
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
        content = (LinearLayout) view.findViewById(R.id.content);
        panel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpand){
                    mHanler.post(new Runnable() {
                        @Override
                        public void run() {
                            content.setVisibility(View.GONE);
                            group_open_icon.setBackgroundResource(R.mipmap.arrow_up);
                        }
                    });
                    isExpand = false;
                }else {
                    mHanler.post(new Runnable() {
                        @Override
                        public void run() {
                            content.setVisibility(View.VISIBLE);
                            group_open_icon.setBackgroundResource(R.mipmap.arrow_down);
                        }
                    });
                    isExpand = true;
                }
            }
        });
    }

    private void setData() {

        //设置数据
        combo_name.setText(""+group.get("group_name"));
        combo_details.setText(""+group.get("group_detail"));

        //设置数据
        fm_navi_need_distance.setText(""+child.get("distance"));
        fm_navi_start.setText(""+child.get("start_name"));
        fm_navi_end .setText(""+child.get("end_name"));
    }

    private void initData(){
        group.put("group_name","行动书屋");
        group.put("group_detail","毗邻红树林国际会展中心，与红树林婚礼广场--皇后广场近在咫尺。你大爷你大爷你大爷");

        child.put("start_name","主大堂");
        child.put("end_name","行动书屋");
        child.put("distance","500");
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
        content.setVisibility(View.VISIBLE);
        group_open_icon.setBackgroundResource(R.mipmap.arrow_down);
        mEnterMapId = emr.getMapId();
    }

    /**
     * 设置是否进入室内外的View
     *
     */
    public void setEnterViewVisible(boolean pVisible) {
        if (pVisible) {
            fm_open_navi_big.setVisibility(GONE);
            fm_enter_inside.setVisibility(VISIBLE);
            fm_open_navi_small.setVisibility(VISIBLE);
        } else {
            fm_enter_inside.setVisibility(GONE);
            fm_open_navi_small.setVisibility(GONE);
            fm_open_navi_big.setVisibility(VISIBLE);
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
        return fm_open_navi_big;
    }

}
