package com.jdjt.mangrove.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InAfter;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBean;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InListener;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.listener.OnRadioChecked;

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer(value = R.layout.activity_longin_and_register_fragment,parent = R.id.center_common, isTitle = true)
public class LonginAndRegisterFragmentActivity extends CommonActivity {
    @InBean
    private LoginFragment loginFragment;
    @InBean
    private RegisterPhoneFragment registerPhoneFragment;
    @InView
    RadioGroup radioGroup;
    @InAfter
    protected void initView() {
        getSupportActionBar().setTitle("");
        startFragmentAdd(loginFragment);
    }
//



    @InListener(ids={R.id.radioGroup},listeners={OnRadioChecked.class})
    public void checkedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rd_login_button:
                Ioc.getIoc().getLogger().e("点击登录");
                startFragmentAdd(loginFragment);
                break;
            case R.id.rd_register_button:
                Ioc.getIoc().getLogger().e("点击注册");
                startFragmentAdd(registerPhoneFragment);
                break;
        }
    }

    public void startFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_register_fragment, fragment);
        fragmentTransaction.commit();
    }

}
