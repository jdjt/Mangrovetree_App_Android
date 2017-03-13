package com.jdjt.mangrove;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fengmap.android.FMMapSDK;
import com.fengmap.android.data.FMDataManager;
import com.fengmap.drpeng.common.ResourcesUtils;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.login.LoginAndRegisterFragmentActivity;
import com.jdjt.mangrove.util.PermissionsChecker;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

/**
 * 欢迎页面
 */

public class WelcomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 919; // 请求码



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE

    };

    protected void init() throws InterruptedException {
        PackageManager pm = getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
//            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
//            versionNumber.setText("Version " + pi.versionName);
            PermissionsChecker mChecker = new PermissionsChecker(this);
            if (mChecker.lacksPermissions(PERMISSIONS)) {
                this.requestPermissions(PERMISSIONS, REQUEST_CODE); // 请求权限

            } else {
                Log.d(WelcomeActivity.this.getLocalClassName(), "权限认证完毕" );
//                Ioc.getIoc().getLogger().e("权限认证完毕");
//            copyMap();

                startActivity();

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
//
//            copyMap();//跳转页面
            try {
                startActivity();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
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

    }


    private void writeMapFile(String mapId) {
        String dstFileName = mapId + ".fmap";
        String srcFileName = "data/" + dstFileName;
        ResourcesUtils.writeRc(this,
                FMDataManager.getFMMapResourceDirectory() + mapId + "/", dstFileName, srcFileName);
    }

    private Handler mHandler = new Handler();
    /**
     * 跳转到主页面
     */
    private  void startActivity() throws InterruptedException {
        Ioc.getIoc().init(MangrovetreeApplication.instance);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                copyMap();
                startActivity(new Intent(WelcomeActivity.this, LoginAndRegisterFragmentActivity.class));
                WelcomeActivity.this.finish();
            }
        },2000);



    }
}
