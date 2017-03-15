package com.jdjt.mangrove.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.view.FlowLayout;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:OperationAdapter
 * @Package com.jdjt.mangrove.adapter
 * @Date 2017/3/15 13:18
 */
public class OperationAdapter extends AppBaseAdapter<Map<String, String>, AppBaseAdapter.BaseViewHolder> {
    Context context;
    BaseViewHolder viewHolder;

    public OperationAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        viewHolder=new BaseViewHolder(View.inflate(getContext(), R.layout.list_operation_item, null));
        return viewHolder;
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position, Map<String, String> data) {
        Ioc.getIoc().getLogger().e("绑定数据中。。"+position);
        TextView textView= holder.getView(R.id.title);
        textView.setText("position"+position);
        initTags(setTageData(data));
    }

    List<Map<String, String>> tagData;

    private List<Map<String, String>> setTageData(Map<String, String> tag) {
        tagData = new ArrayList<>();
        tagData.add(tag);
        return tagData;
    }


    /**
     * 设置标签组
     *
     * @param getData
     */
    private void initTags(List<Map<String, String>> getData) {
        //新建适配器

        FlowLayout gl_tags = (FlowLayout) viewHolder.getRootView().findViewById(R.id.gl_tags);
        gl_tags.setHorizontalSpacing(1);
        gl_tags.setVerticalSpacing(1);
        TagsAdapter tagsAdapter = new TagsAdapter(context);
        tagsAdapter.setDataSource(getData);
        gl_tags.setAdapter(tagsAdapter);
    }

}
