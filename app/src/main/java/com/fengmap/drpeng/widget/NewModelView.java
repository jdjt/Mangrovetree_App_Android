package com.fengmap.drpeng.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
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
    TextView fm_open_navi,fm_enter_inside;
    LinearLayout navi_view;
    private HashMap<String,String> group = new HashMap<>();
    private HashMap<String,String> child = new HashMap<>();

    public NewModelView(Context context) {
        super(context);
        this.mContext = context;
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

}
