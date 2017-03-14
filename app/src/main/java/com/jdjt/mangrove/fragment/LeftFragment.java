package com.jdjt.mangrove.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.login.PesonalInfoActivity;
import com.jdjt.mangrove.login.SettingActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBinder;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
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

    @InView(value = R.id.account_islogin, binder = @InBinder(listener = OnClick.class, method = "click"))
    LinearLayout account_islogin; //用户中心
    @InView(value = R.id.ll_account_setting_layout, binder = @InBinder(listener = OnClick.class, method = "click"))
    LinearLayout ll_account_setting_layout;//设置

    private void click(View view) {
        Intent intent=new Intent(getActivity(), PesonalInfoActivity.class);
        switch (view.getId()) {
//                //跳转到用户中心
            case R.id.account_islogin:
                intent.putExtra("title","个人中心");
                startActivity(intent);
                break;
            //跳转到设置
            case R.id.ll_account_setting_layout:
                intent=new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("title","设置");
                startActivity(intent);

                break;
        }

    }

}
