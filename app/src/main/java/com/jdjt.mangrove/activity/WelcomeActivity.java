package com.jdjt.mangrove.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.fengmap.android.FMMapSDK;
import com.fengmap.android.data.FMDataManager;
import com.fengmap.android.wrapmv.Tools;
import com.fengmap.drpeng.FMAPI;
import com.fengmap.drpeng.OutdoorMapActivity;
import com.fengmap.drpeng.common.ResourcesUtils;
import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.activity.base.SysBaseAppCompatActivity;
import com.jdjt.mangrovetreelibray.utils.PermissionsChecker;

/**
 *欢迎页面
 */
public class WelcomeActivity extends SysBaseAppCompatActivity {
    private static final int REQUEST_CODE = 919; // 请求码

    private Handler mHandler = new Handler();

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_welcome;
    }
    /**
     * @method 需要校验的权限
     */
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_WIFI_STATE

    };
    @Override
    protected void initView() {

        PackageManager pm = getPackageManager();
        try {
            Log.d(WelcomeActivity.this.getLocalClassName(),"this.getPackageName() ："+this.getPackageName());
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setText("Version " + pi.versionName);
            PermissionsChecker  mChecker = new PermissionsChecker(this);
            if (mChecker.lacksPermissions(PERMISSIONS)) {
                this.requestPermissions(PERMISSIONS,REQUEST_CODE); // 请求权限
            }else{
                copyMap();
                startActivity();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int resultCode:grantResults){
            Log.d(WelcomeActivity.this.getLocalClassName(),"当前返回的code"+resultCode);
            //如果当前的返回权限为拒绝 -1 那么关比当前窗口并 跳出方法体
            if(resultCode == PackageManager.PERMISSION_DENIED){
                finish();
                return;
            }
        }
        //通过权限校验 并且赋予权限后触发跳转到 主页面
        if (requestCode == REQUEST_CODE) {
            startActivity();//跳转页面
        }
    }

    void copyMap() {

        FMMapSDK.initResource();

        writeMapFile("79980");
        writeMapFile("79981");
        writeMapFile("79982");
        writeMapFile("70144");
        writeMapFile("70145");
        writeMapFile("70146");
        writeMapFile("70147");
        writeMapFile("70148");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle b = new Bundle();
                b.putString(FMAPI.ACTIVITY_WHERE, WelcomeActivity.class.getName());
                b.putString(FMAPI.ACTIVITY_MAP_ID, Tools.OUTSIDE_MAP_ID);
                FMAPI.instance().gotoActivity(WelcomeActivity.this, OutdoorMapActivity.class, b);
                WelcomeActivity.this.finish();
            }
        }, 500);

    }


    private void writeMapFile(String mapId) {
        String dstFileName = mapId + ".fmap";
        String srcFileName = "data/" + dstFileName;
        ResourcesUtils.writeRc(this,
                FMDataManager.getFMMapResourceDirectory() + mapId + "/", dstFileName, srcFileName);
    }


    /**
     * 跳转到主页面
     */
    private  void startActivity(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle b = new Bundle();
                b.putString(FMAPI.ACTIVITY_WHERE, WelcomeActivity.class.getName());
                b.putString(FMAPI.ACTIVITY_MAP_ID, Tools.OUTSIDE_MAP_ID);
                FMAPI.instance().gotoActivity(WelcomeActivity.this, OutdoorMapActivity.class, b);
                WelcomeActivity.this.finish();
            }
        }, 500);
    }
}
