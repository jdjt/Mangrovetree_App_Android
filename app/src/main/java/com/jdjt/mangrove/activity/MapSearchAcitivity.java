package com.jdjt.mangrove.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.android.FMDevice;
import com.fengmap.android.wrapmv.Tools;
import com.fengmap.drpeng.FMAPI;
import com.fengmap.drpeng.IndoorMapActivity;
import com.fengmap.drpeng.OutdoorMapActivity;
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
    private TextView mMoreView;
    private SearchListAdapter mAdapter = null;
    private String mMapId;//地图id
    private int mGroupId; //组id
    private int index = 0;
    List<Stores> list = null; //地图poi数据
    FMDBMapElementOveridDao fbd = null;
    public static int SEARCHTYPE_NAME = 0; //查询类别 0 按name 查询
    public static int SEARCHTYPE_SUBNAME = 1;//查询类别 1 按subtypname 查询
    int type = 0;//默认为0 按名字检索，1 为按subtypename 检索
    public int countmax = 10; //最大条数

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
        MenuItemCompat.setOnActionExpandListener(menuItem, this);
        return super.onCreateOptionsMenu(menu);
    }


    @Init
    private void initView() {
        list = new ArrayList<>(0);
        fbd = new FMDBMapElementOveridDao();
        initList();
        initViewGroup();
    }

    /**
     * 初始化标签
     */
    private void initViewGroup() {
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

    /**
     * 初始化listview
     */
    private void initList() {
        // 通过传入mCursor，将联系人名字放入listView中。
        Bundle b = getIntent().getExtras();
        mMapId = b.getString(FMAPI.ACTIVITY_MAP_ID, null);
        mGroupId = b.getInt(FMAPI.ACTIVITY_MAP_GROUP_ID, 0);
        search_listView = (ListView) findViewById(R.id.search_listView);
        mMoreView = new TextView(this);
        mMoreView.setText("查看更多搜索结果");
        mMoreView.setTextSize(14);
        mMoreView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                (int) (50 * FMDevice.getDeviceDensity())));
        mMoreView.setTextColor(Color.parseColor("#90C98C"));
        mMoreView.setGravity(Gravity.CENTER);
        search_listView.addFooterView(mMoreView);
        mAdapter = new SearchListAdapter(this);
        mAdapter.setDataSource(list);
        search_listView.setAdapter(mAdapter);
        search_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 加载更多
                if (position >= mAdapter.getCount()) {
                    if (isLoadCompleted) {
                        return;
                    }
                    loadMore();
                    return;
                }
                gotoMap(mAdapter.getItem(position));
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Ioc.getIoc().getLogger().e("onQueryTextSubmit 当前查询条件 " + type);
        getData(query);
        return true;
    }

    /**
     * 调用查询方法
     *
     * @param content
     * @param type
     */
    public void search(String content, int type) {
        clearData();
        this.type = type;
        searchView.setQuery(content, true);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Ioc.getIoc().getLogger().e("onQueryTextChange  当前查询条件 " + type);
        if (TextUtils.isEmpty(newText)) {
            clearData();
        } else {
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

    /**
     * 获取查询数据
     *
     * @param subtypename 标签名称
     */
    private void getData(String subtypename) {
        List<Stores> dataList = new ArrayList<>();
        if (type == SEARCHTYPE_NAME) {
            dataList = fbd.queryStoresByName(subtypename, index * countmax);
        } else if (type == SEARCHTYPE_SUBNAME) {
            dataList = fbd.queryPrioritySubTypename(mMapId, mGroupId, subtypename, index * countmax);
        }
        Ioc.getIoc().getLogger().e("查询条数 ：" + dataList.size());
        ++index;
        if (dataList.isEmpty()) {
            isLoadCompleted = true;
            index = 0;
            mMoreView.setText("没有更多数据了");
            mMoreView.setTextColor(Color.parseColor("#999999"));
        } else {
            isLoadCompleted = false;
            mMoreView.setText("查看更多搜索结果");
            mMoreView.setTextColor(Color.parseColor("#90C98C"));
            mMoreView.setVisibility(View.VISIBLE);
        }
        list.addAll(dataList);
        Ioc.getIoc().getLogger().e("查询条数 ：" + list.size());
        mAdapter.notifyDataSetChanged();
    }

    boolean isLoadCompleted = false;

    private void loadMore() {
        String content = searchView.getQuery().toString().trim();
        if (content == null || content.equals("")) {
            return;
        }
        getData(content);


    }

    /**
     * 清除数据
     */
    private void clearData() {
        //初始化显示类型
        type = SEARCHTYPE_NAME;
        index = 0;//还原指针
        list.clear();
        search_listView.clearTextFilter();
        search_listView.setVisibility(View.GONE);

        isLoadCompleted = false;
        mAdapter.setDataSource(list);
        searchView.clearFocus();
    }

    /**
     * 蜂鸟封装的方法，未处理原来逻辑
     *
     * @param s
     */
    private void gotoMap(Stores s) {
        boolean isInside = Tools.isInsideMap(mMapId);
        Bundle b = new Bundle();
        String className = this.getClass().getName();
        Ioc.getIoc().getLogger().e("当前的name" + s.getName());
        b.putSerializable(className, s);
        b.putString(FMAPI.ACTIVITY_WHERE, className);
        if (isInside) {
            FMAPI.instance().gotoActivity(this,
                    IndoorMapActivity.class,
                    b);
        } else {
            FMAPI.instance().gotoActivity(this,
                    OutdoorMapActivity.class,
                    b);
        }
        this.finish();
    }
}
