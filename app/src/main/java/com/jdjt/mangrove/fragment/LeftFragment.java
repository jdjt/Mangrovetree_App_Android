package com.jdjt.mangrove.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.login.PesonalInfoActivity;
import com.jdjt.mangrove.login.SettingActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBinder;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.listener.OnClick;

/**
 * @author wmy
 * @Description:
 * @FileName:LeftFragment
 * @Package com.jdjt.mangrove.fragment
 * @Date 2017/3/13 15:43
 */
@InLayer(R.layout.header_nav)
public class LeftFragment extends Fragment {

    @InView(R.id.account_islogin)
    LinearLayout account_islogin; //用户中心
    @InView(R.id.ll_account_setting_layout)
    LinearLayout ll_account_setting_layout;//设置


    @Init
    public void init() {
        account_islogin.setOnClickListener(onClickListener);
        ll_account_setting_layout.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
//                //跳转到用户中心
                case R.id.account_islogin:
                    Toast.makeText(getContext(), "好的", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), PesonalInfoActivity.class));
                    break;
                //跳转到设置
                case R.id.ll_account_setting_layout:
                    Toast.makeText(getContext(), "不好", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                    break;
            }
        }
    };
}
//    protected LayoutInflater inflater;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        this.inflater = inflater;
//        View rootView = inflater.inflate(R.layout.header_nav, container, false);
//
//        return rootView;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }


