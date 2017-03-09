package com.jdjt.mangrovetreelibray.activity.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author wmy
 * @Description: 项目基础 activity  用于公用控件控制
 * @version:1.0
 * @FileName:SysBaseAppCompatActivity
 * @Package com.jdjt.mangrovetreelibray.Activity.base
 * @Date 2017/3/9 10:24
 */
public abstract class SysBaseAppCompatActivity extends AppCompatActivity {
    //定义遮罩层
    protected ProgressDialog dialog = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(initPageLayoutID()!=0){
            setContentView(initPageLayoutID());
        }
        initView();
    }

    /**
     * 返回主布局id
     */
    protected abstract int initPageLayoutID();

    /**
     * 执行初始化 view
     * @return
     */
    protected abstract void initView();


    /**
     * 显示进度条
     *
     * @param title 标题
     * @param msg   内容
     */
    protected void showProgress(String title, String msg) {
        if (dialog == null) {
            doCreateProgress();
        }

        dialog.setTitle(title);
        dialog.setMessage(msg);

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 单独开启线程显示 dialog
     *
     * @param handler 创建线程
     * @param title   标题
     * @param msg     内容
     */
    protected void showProgress(Handler handler, String title, String msg) {
        if (dialog == null) {
            doCreateProgress();
        }

        dialog.setTitle(title);
        dialog.setMessage(msg);

        if (!dialog.isShowing()) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.show();
                }
            });
        }
    }

    /**
     * 初始化dialog
     */
    private void doCreateProgress() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(dialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
    }

    /**
     * 关闭进度条
     */
    protected void closeProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog=null;
    }

    /**
     * 关闭进度条
     * @param handler
     */
    protected void closeProgress(Handler handler) {
        if (dialog != null && dialog.isShowing()) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        }
        dialog=null;
    }

    /**
     * activity销毁时 同时销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeProgress();
    }
}
