package com.jdjt.mangrove;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.fengmap.android.FMMapSDK;
import com.fengmap.android.data.FMDataManager;
import com.fengmap.drpeng.common.ResourcesUtils;
import com.jdjt.mangrove.login.LonginAndRegisterFragmentActivity;
import com.jdjt.mangrove.util.PermissionsChecker;
import com.jdjt.mangrovetreelibray.ioc.annotation.InBack;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

/**
 * 欢迎页面
 */
@InLayer(R.layout.activity_welcome)
public class WelcomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 919; // 请求码

    private Handler mHandler = new Handler();

//    @Override
//    protected int initPageLayoutID() {
//
//        return R.layout.activity_welcome;
//    }
    /**
     * @method 需要校验的权限
     */
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE

    };

    @Init
    @InBack
    protected void init() throws InterruptedException {
        PackageManager pm = getPackageManager();
        try {
            Log.d(WelcomeActivity.this.getLocalClassName(), "this.getPackageName() ：" + this.getPackageName());
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setText("Version " + pi.versionName);
            PermissionsChecker mChecker = new PermissionsChecker(this);
            if (mChecker.lacksPermissions(PERMISSIONS)) {
                this.requestPermissions(PERMISSIONS, REQUEST_CODE); // 请求权限
            } else {
//            copyMap();
            }
            Thread.sleep(3000);
            startActivity(new Intent(WelcomeActivity.this, LonginAndRegisterFragmentActivity.class));
            finish();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultCode : grantResults) {
            Log.d(WelcomeActivity.this.getLocalClassName(), "当前返回的code" + resultCode);
            //如果当前的返回权限为拒绝 -1 那么关比当前窗口并 跳出方法体
            if (resultCode == PackageManager.PERMISSION_DENIED) {
                finish();
                return;
            }
        }
        //通过权限校验 并且赋予权限后触发跳转到 主页面
        if (requestCode == REQUEST_CODE) {
//            copyMap();//跳转页面
        }
        startActivity(new Intent(WelcomeActivity.this, LonginAndRegisterFragmentActivity.class));
    }

    void copyMap() {
//        Ioc.getIoc().init(getApplication());
//        MangrovetreeApplication.getInstance().plugLoad();
        FMMapSDK.initResource();
        writeMapFile("79980");
        writeMapFile("79981");
        writeMapFile("79982");
        writeMapFile("70144");
        writeMapFile("70145");
        writeMapFile("70146");
        writeMapFile("70147");
        writeMapFile("70148");

    }


    private void writeMapFile(String mapId) {
        String dstFileName = mapId + ".fmap";
        String srcFileName = "data/" + dstFileName;
        ResourcesUtils.writeRc(this,
                FMDataManager.getFMMapResourceDirectory() + mapId + "/", dstFileName, srcFileName);
    }

//
//    /**
//     * 跳转到主页面
//     */
//    private  void startActivity(){
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Bundle b = new Bundle();
//                b.putString(FMAPI.ACTIVITY_WHERE, WelcomeActivity.class.getName());
//                b.putString(FMAPI.ACTIVITY_MAP_ID, Tools.OUTSIDE_MAP_ID);
//                FMAPI.instance().gotoActivity(WelcomeActivity.this, LonginAndRegisterFragmentActivity.class, b);
//                WelcomeActivity.this.finish();
//            }
//        }, 500);
//    }
}
