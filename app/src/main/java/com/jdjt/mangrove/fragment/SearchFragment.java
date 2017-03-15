package com.jdjt.mangrove.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.adapter.OperationAdapter;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);

    }
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        NestedScrollView scrollableView = new NestedScrollView(getActivity());
//        TextView textView = new TextView(getActivity());
//        textView.setTextColor(Color.RED);
//        textView.setGravity(Gravity.CENTER);
//        textView.setText(mText);
//        scrollableView.addView(textView);
        view =inflater.inflate(R.layout.fragment_search_map,null,true);
        init();
        return view;
    }
    List<Map<String,String>> list;
    private ListView lv_list;
    List<Map<String, String>> tagData;

    OperationAdapter adapter;

//    @Init
    private void init(){
        Ioc.getIoc().getLogger().e("初始化搜索页");

        lv_list= (ListView) view.findViewById(R.id.lv_list);
        adapter=new OperationAdapter(getActivity());
        lv_list.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
        lv_list.setAdapter(adapter);
        initData();
        adapter.setDataSource(list);
    }


    private void initData(){
        list=new ArrayList<>();
        for(int i=0;i<7;i++){
            Map map=new HashMap();
            map.put("title","title"+i);
            map.put("code","code"+i);
            list.add(map);
        }

    }

//    /**
//     * 设置标签组
//     *
//     * @param getData
//     */
//    private void initTags(List<Map<String, String>> getData) {
//        //新建适配器
//        FlowLayout gl_tags = (FlowLayout) getActivity().findViewById(R.id.gl_tags);
//        gl_tags.setHorizontalSpacing(1);
//        gl_tags.setVerticalSpacing(1);
//        TagsAdapter tagsAdapter = new TagsAdapter(getActivity());
//        tagsAdapter.setDataSource(tagData);
//        gl_tags.setAdapter(tagsAdapter);
//    }
//
//    /**
//     * 删除标签
//     *
//     * @param getData
//     */
//    private void removeTag(Map<String, Object> getData) {
//        FlowLayout gl_tags = (FlowLayout) getActivity().findViewById(R.id.gl_tags);
//        for (int i = 0; i < tagData.size(); i++) {
//            if (getData.get("title").equals(tagData.get(i).get("title") + "")) {
//                gl_tags.removeViewAt(i);
//                tagData.remove(i);
//            }
//        }
//    }
}