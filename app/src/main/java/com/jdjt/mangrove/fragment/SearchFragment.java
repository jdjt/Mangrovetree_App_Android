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
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {

    private String title ;
    public static final String ARGUMENT ="title";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getActivity().getIntent().getStringExtra(ARGUMENT);
        Bundle bundle = getArguments();
        if (bundle != null)
            title = bundle.getString(ARGUMENT);
    }
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_search_map,null,true);
        init();
        return view;
    }
    List<HashMap<String,String>> list;
    private ListView lv_list;
    ArrayList<Map<String, String>> tagData;

    OperationAdapter adapter;

//    @Init
    private void init(){
        Ioc.getIoc().getLogger().e(getFromAssets("mangrove/vacation.json"));
        //"本酒店度假设施", "本酒店服务设施"
        if(title.equals("本酒店度假设施")){
            list= Handler_Json.JsonToCollection(getFromAssets("mangrove/vacation.json"));
        }else{
            list= Handler_Json.JsonToCollection(getFromAssets("mangrove/service.json"));
        }

        lv_list= (ListView) view.findViewById(R.id.lv_list);
        adapter=new OperationAdapter(getActivity());
        lv_list.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
        lv_list.setAdapter(adapter);
        adapter.setDataSource(list);
    }
    /**
     * 获取Assets下的文件资源
     *
     * @param fileName 文件名称
     * @return String
     */
    public String getFromAssets(String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";

            while ((line = bufReader.readLine()) != null)
                Result += line;

        } catch (Exception e) {
            Ioc.getIoc().getLogger().e(e);
        }
        return Result;
    }





}