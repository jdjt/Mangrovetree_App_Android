package com.jdjt.mangrove.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.util.StatusBarUtil;
import com.jdjt.mangrovetreelibray.ioc.annotation.InPLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;
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
    private void setTranslucentStatus(boolean on) {
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
        mActionBarToolbar.setNavigationIcon(R.mipmap.icon_back);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        StatusBarUtil.StatusBarLightMode(this);
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

    protected void setStatusBar() {
        setTranslucentStatus( R.color.title_bg);
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.title_bg), 0);
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
        initActionBar();
        setStatusBar();
    }

    /**
     * @method 查询activitycode
     */
    public String getModelActivityCode(){
        String code = "";

        return code;
    }
}
