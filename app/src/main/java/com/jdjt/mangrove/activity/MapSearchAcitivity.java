package com.jdjt.mangrove.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.adapter.TabFragmentAdapter;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.fragment.SearchFragment;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

import java.util.ArrayList;
import java.util.List;

@InLayer(value = R.layout.activity_map_search_acitivity, parent = R.id.center_common)
public class MapSearchAcitivity extends CommonActivity {
    String[] titles = {"本酒店度假设施", "本酒店服务设施"}; //, "本酒店服务设施"
    @InView
    ViewPager container;
    @InView
    TabLayout tabs;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map_search_acitivity, menu);
        return super.onCreateOptionsMenu(menu);
    }
//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
////        switch (item.getItemId()) {
////            case R.id.menu_item_search:
//////                startActivity(new Intent(StartSearchActivity.this, PhotoGalleryActivity.class));
////                return true;
////        }
//        return super.onMenuItemSelected(featureId, item);
//    }
    @Init
    private void initView() {
//        getActionBarToolbar().findViewById(R.id.toolbar_title).setVisibility(View.GONE);
//        getActionBarToolbar().findViewById(R.id.searchView).setVisibility(View.VISIBLE);
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < titles.length; i++) {
            Fragment fragment = new SearchFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        container.setAdapter(new TabFragmentAdapter(fragments, titles, getSupportFragmentManager(), this));
    // 初始化
    // 将ViewPager和TabLayout绑定
        tabs.setupWithViewPager(container);
//        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
//        tabs.setTabTextColors(Color.GRAY, Color.WHITE);
    }

}
