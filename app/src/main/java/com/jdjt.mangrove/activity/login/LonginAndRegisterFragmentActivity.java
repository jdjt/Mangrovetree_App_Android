package com.jdjt.mangrove.activity.login;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.activity.base.SysBaseAppCompatActivity;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBean;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InListener;
import com.jdjt.mangrovetreelibray.ioc.annotation.InView;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.listener.OnRadioChecked;

/**
 * Created by huyanan on 2017/3/9.
 */
@InLayer(R.layout.activity_longin_and_register_fragment)
public class LonginAndRegisterFragmentActivity extends AppCompatActivity {
    @InBean
    private LoginFragment loginFragment;
    @InBean
    private RegisterPhoneFragment registerPhoneFragment;
    @InView
    RadioGroup radioGroup;
    @Init
    protected void initView() {
        startFragmentAdd(loginFragment);
    }
//



    @InListener(ids={R.id.radioGroup},listeners={OnRadioChecked.class})
    public void checkedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.login_button:
                Ioc.getIoc().getLogger().e("点击登录");
                startFragmentAdd(loginFragment);
                break;
            case R.id.register_button:
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
