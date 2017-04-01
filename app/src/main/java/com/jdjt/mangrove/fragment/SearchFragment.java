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
import com.jdjt.mangrove.util.CommonUtils;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.util.HashMap;
import java.util.List;

/**
 * 搜索标签页
 */
public class SearchFragment extends Fragment {
    View view;
    private String title ;
    public static final String ARGUMENT ="title";
    public List<HashMap<String,String>> list;
    private ListView lv_list;
    private  OperationAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getActivity().getIntent().getStringExtra(ARGUMENT);
        Bundle bundle = getArguments();
        if (bundle != null)
            title = bundle.getString(ARGUMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_search_map,null,true);
        init();
        return view;
    }

    private void init(){
        Ioc.getIoc().getLogger().e(CommonUtils.getFromAssets(getActivity(),"mangrove/vacation.json"));
        //"本酒店度假设施", "本酒店服务设施"
        if(title.equals("本酒店度假设施")){
            list= Handler_Json.JsonToCollection(CommonUtils.getFromAssets(getActivity(),"mangrove/vacation.json"));
        }else{
            list= Handler_Json.JsonToCollection(CommonUtils.getFromAssets(getActivity(),"mangrove/service.json"));
        }

        lv_list= (ListView) view.findViewById(R.id.lv_list);
        adapter=new OperationAdapter(getActivity());
        lv_list.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
        lv_list.setAdapter(adapter);
        adapter.setDataSource(list);
    }






}