package com.jdjt.mangrove.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.widget.ListAdapter;
import android.widget.TableLayout;

/**
 * @author wmy
 * @Description:
 * @FileName:TagTableLayout
 * @Package com.jdjt.exchange.view
 * @Date 2016/9/9 18:04
 */
public class TagTableLayout extends TableLayout {
    public TagTableLayout(Context context) {
        super(context);
    }

    ListAdapter adapter;

    public void setDataAdapter(@NonNull ListAdapter adapter) {
        if (adapter == null) throw new NullPointerException("FlowLayout.setAdapter不能传空参数");
        this.adapter = adapter;
        adapter.registerDataSetObserver(new DataSetObserver() {

            @Override
            public void onChanged() {
                changeViews();
            }

            @Override
            public void onInvalidated() {
                changeViews();
            }
        });
    }

    private void changeViews() {

    }
}
