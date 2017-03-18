package com.jdjt.mangrove.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.entity.Stores;
import com.jdjt.mangrove.view.FlowLayout;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_String;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:OperationAdapter
 * @Package com.jdjt.mangrove.adapter
 * @Date 2017/3/15 13:18
 */
public class SearchListAdapter extends AppBaseAdapter<Stores, AppBaseAdapter.BaseViewHolder> {
    Context context;
    BaseViewHolder viewHolder;
    TagsAdapter tagsAdapter = null;
    private Inflater mInflater;

    public SearchListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        viewHolder = new BaseViewHolder(View.inflate(getContext(), R.layout.activity_map_search_list_item, null));

        return viewHolder;
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position, Stores data) {
        TextView title = holder.getView(R.id.tv_title);
        TextView content = holder.getView(R.id.tv_content);
        title.setText(data.getName());
        content.setText(data.getAddress());
    }


}
