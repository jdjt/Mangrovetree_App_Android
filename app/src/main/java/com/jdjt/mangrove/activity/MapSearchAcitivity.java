package com.jdjt.mangrove.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.fengmap.drpeng.db.FMDBMapElementOveridDao;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.adapter.SearchListAdapter;
import com.jdjt.mangrove.adapter.TabFragmentAdapter;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.entity.Stores;
import com.jdjt.mangrove.fragment.SearchFragment;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.util.ArrayList;
import java.util.List;

@InLayer(value = R.layout.activity_map_search_acitivity, parent = R.id.center_common)
public class MapSearchAcitivity extends CommonActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {
    String[] titles = {"本酒店度假设施", "本酒店服务设施"}; //, "本酒店服务设施"
    @InView
    ViewPager container;
    @InView
    SegmentTabLayout tabs;
    SearchView searchView;
    private ListView search_listView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map_search_acitivity, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_search);//在菜单中找到对应控件的item
        //获得searchView对象
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_item_search));
        // 设置该SearchView默认是否自动缩小为图标
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(true);
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Toast.makeText(MapSearchAcitivity.this, "position", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MapSearchAcitivity.this, "onClose", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        searchView.setQueryHint("请输入您要去的地方");
        //获得searchManager对象
//        SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        MenuItemCompat.setOnActionExpandListener(menuItem, this);
        return super.onCreateOptionsMenu(menu);
    }

    SearchListAdapter mAdapter = null;

    @Init
    private void initView() {
        // 通过传入mCursor，将联系人名字放入listView中。

        search_listView = (ListView) findViewById(R.id.search_listView);

        mAdapter = new SearchListAdapter(this);

        search_listView.setAdapter(mAdapter);
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < titles.length; i++) {
            Fragment fragment = new SearchFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        container.setAdapter(new TabFragmentAdapter(fragments, titles, getSupportFragmentManager(), this));
        //设置tab 宽高
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = 104;
        int margin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
        layoutParams.setMargins(margin, margin, margin, margin);
        tabs.setLayoutParams(layoutParams);
        tabs.setTabData(titles);
        tabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                container.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabs.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        container.setCurrentItem(0);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        getData(query);
        return true;
    }

    public void search(String content) {
        searchView.setQuery(content, true);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(TextUtils.isEmpty(newText)){
            searchView.clearFocus();
            list.clear();
            mAdapter.setDataSource(list);
            search_listView.clearTextFilter();
            search_listView.setVisibility(View.GONE);

        }else {
            search_listView.setVisibility(View.VISIBLE);
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(this, "onExpand", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Toast.makeText(this, "onExpand", Toast.LENGTH_LONG).show();
        return false;
    }

    List<Stores> list=null;
    private void getData(String subtypename) {
        FMDBMapElementOveridDao fbd = new FMDBMapElementOveridDao();
        list = fbd.queryStoresByTypeName(subtypename);
        mAdapter.setDataSource(list);
    }
}
