package com.jdjt.mangrove.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.view.FlowLayout;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_String;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * @author wmy
 * @Description:
 * @FileName:OperationAdapter
 * @Package com.jdjt.mangrove.adapter
 * @Date 2017/3/15 13:18
 */
public class OperationAdapter extends AppBaseAdapter<HashMap<String, String>, AppBaseAdapter.BaseViewHolder> {
    Context context;
    BaseViewHolder viewHolder;
    TagsAdapter tagsAdapter = null;
    int screenHeight;
    int screenWidth;
    private Inflater mInflater;

    public OperationAdapter(Context context) {
        super(context);
        this.context = context;
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        viewHolder = new BaseViewHolder(View.inflate(getContext(), R.layout.list_operation_item, null));

        return viewHolder;
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position, HashMap<String, String> data) {
        if (Handler_String.isBlank(data.get("title_name"))) {
            View view = holder.getView(R.id.ll_title);
            view.setVisibility(View.GONE);
        } else {
            TextView textView = holder.getView(R.id.title);
            textView.setText(data.get("title_name") + "");

        }
        initTags(setTageData(data), holder);
    }

    List<Map<String, String>> tagData;

    private List<Map<String, String>> setTageData(Map tag) {
//        Ioc.getIoc().getLogger().e(tag.get("items"));
        String items = (String) tag.get("items");
        tagData = new ArrayList<>();
        Map map = null;
        for (String item : items.split(",")) {
            map = new HashMap();
            map.put("title", item);
            tagData.add(map);
        }
        return tagData;
    }


    /**
     * 设置标签组
     *
     * @param getData
     */
    private void initTags(List<Map<String, String>> getData, BaseViewHolder holder) {
        //新建适配器

        FlowLayout gl_tags = (FlowLayout) holder.getView(R.id.gl_tags);

        tagsAdapter = new TagsAdapter(context);
        gl_tags.setHorizontalSpacing(screenWidth / 28);
        gl_tags.setVerticalSpacing(10);

        tagsAdapter.setDataSource(getData);
        gl_tags.setAdapter(tagsAdapter);
    }

}
