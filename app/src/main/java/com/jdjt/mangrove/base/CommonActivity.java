package com.jdjt.mangrove.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.android.map.geometry.FMTotalMapCoord;
import com.jdjt.mangrove.R;
import com.jdjt.mangrove.WelcomeActivity;
import com.jdjt.mangrove.login.LoginAndRegisterFragmentActivity;
import com.jdjt.mangrove.util.StatusUtil;
import com.jdjt.mangrovetreelibray.ioc.annotation.InPLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Network;
import com.jdjt.mangrovetreelibray.utils.SystemStatusManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author wmy
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:CommonActivity
 * @Package com.jdjt.mangrove.base
 * @Date 2017/3/13 13:23
 */
@InPLayer(R.layout.activity_common)
public class CommonActivity extends AppCompatActivity {
    private Toolbar mActionBarToolbar;

    protected ProgressDialog dialog = null;
    public boolean isInHotel = false;
    public boolean isFirstLoad = true;
    public boolean isShowDetail = false;

    public static final String EXTRA_TITLE = "title";

    SweetAlertDialog pDialog = null;

    public void showLoading() {
        if (pDialog == null)
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("加载中...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void showConfirm(String msg, SweetAlertDialog.OnSweetClickListener listener) {
        pDialog = new SweetAlertDialog(this);
        pDialog.setTitleText("温馨提示")
                .setContentText(msg)
                .setCancelText("取消")
                .setConfirmText("确定")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }).setConfirmClickListener(listener)
                .show();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
////                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }
    /**
     * activity销毁时 同时销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    /**
     * 修改状态栏
     *
     * @param on
     */
    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 设置状态栏背景状态
     */
    public void setTranslucentStatus(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(true);
            SystemStatusManager tintManager = new SystemStatusManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);//状态栏无背景
            // 设置状态栏的文字颜色
            tintManager.setStatusBarDarkMode(true, this);
        }

    }

    // 获取顶部status bar 高度
    public int getStatusBarHeight() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 初始化标题栏
     */
    protected void initActionBar() {
        if (getActionBarToolbar() == null) {
            return;
        }
//        mActionBarToolbar.setNavigationIcon(getDrawable(R.mipmap.icon_back));
        mActionBarToolbar.findViewById(R.id.app_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mActionBarToolbar.setNavigationIcon(R.mipmap.icon_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //使自定义的普通View能在title栏显示，即actionBar.setCustomView能起作用，对应ActionBar.DISPLAY_SHOW_CUSTOM
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        //这个小于4.0版本的默认值为true的。但是在4.0及其以上是false,决定左上角的图标是否可以点击。。
        getSupportActionBar().setHomeButtonEnabled(true);
        //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，
        // 对应id为android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME
        //其中setHomeButtonEnabled和setDisplayShowHomeEnabled共同起作用，
        //如果setHomeButtonEnabled设成false，即使setDisplayShowHomeEnabled设成true，图标也不能点击
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //对应ActionBar.DISPLAY_SHOW_TITLE。
                getSupportActionBar().setDisplayUseLogoEnabled(false);

        if (mActionBarToolbar != null) {
            TextView textView = (TextView) mActionBarToolbar.findViewById(R.id.toolbar_title);
            textView.setText(getTitle());
        }
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }


    public void setActionBarTitle(String string) {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        }
        TextView title = (TextView) mActionBarToolbar.findViewById(R.id.toolbar_title);
        title.setText(string);
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void setStatusBar() {
        setTranslucentStatus( R.color.title_bg);
//        StatusBarUtil.setStatusBarColor(this, getColor(R.color.title_bg));
        StatusUtil.StatusBarLightMode(this);
//        StatusBarUtil.setColor(this,getColor(R.color.title_bg),0);
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            showConfirm("是否确定退出？", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                    sweetAlertDialog.dismiss();
                    System.exit(0);

                }
            });
        } else {
            finish();
        }
    }

    @Init
    private void initActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initActionBar();
        setStatusBar();
        if(!Handler_Network.isNetworkAvailable(this)){
            Toast.makeText(this, "当前没有可用网络", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @method 查询activitycode
     */
    public String getModelActivityCode(){
        String code = "";

        return code;
    }
    /**
     * @method 设置默认位置坐标点为主大堂
     */
    public FMTotalMapCoord getdefaultcoord() {
        //默认位置坐标点
        FMTotalMapCoord defaultPosition = new FMTotalMapCoord(1.21884255544187E7, 2071275.90186538, 0.0);
        defaultPosition.setGroupId(1);
        defaultPosition.setMapId("79980");
        return defaultPosition;
    }
}
