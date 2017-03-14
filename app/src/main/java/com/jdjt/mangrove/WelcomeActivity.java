package com.jdjt.mangrove;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.android.FMMapSDK;
import com.fengmap.android.data.FMDataManager;
import com.fengmap.android.wrapmv.Tools;
import com.fengmap.drpeng.CrashHandler;
import com.fengmap.drpeng.FMAPI;
import com.fengmap.drpeng.OutdoorMapActivity;
import com.fengmap.drpeng.common.ResourcesUtils;
import com.google.gson.JsonObject;
import com.jdjt.mangrove.application.MangrovetreeApplication;
import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrove.common.HeaderConst;
import com.jdjt.mangrove.login.LoginAndRegisterFragmentActivity;
import com.jdjt.mangrove.util.PermissionsChecker;
import com.jdjt.mangrovetreelibray.ioc.annotation.InHttp;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Json;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_String;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 欢迎页面
 */

public class WelcomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 919; // 请求码
    private boolean isLogin = false;

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
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(this.getPackageName(), 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setText("Version " + pi.versionName);
            PermissionsChecker mChecker = new PermissionsChecker(this);
            if (mChecker.lacksPermissions(PERMISSIONS)) {
                this.requestPermissions(PERMISSIONS, REQUEST_CODE); // 请求权限

            } else {
                Log.d(WelcomeActivity.this.getLocalClassName(), "权限认证完毕");
                login();
            }
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
            login();
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

    private void login() {
        Ioc.getIoc().init(MangrovetreeApplication.instance);
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getAppExceptionHandler(this));
        String account = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "account", 0);
        String password = Handler_SharedPreferences.getValueByName(Constant.HttpUrl.DATA_USER, "password", 0);
        if (Handler_String.isBlank(account)) {
            startActivity();
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("account", account);
        jsonObject.addProperty("password", password);
        MangrovetreeApplication.instance.http.u(this).login(jsonObject.toString());
    }

    @InHttp(Constant.HttpUrl.LOGIN_KEY)
    public void result(ResponseEntity entity) {
        if (entity.getStatus() == FastHttp.result_net_err) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        if (entity.getContentAsString() == null || entity.getContentAsString().length() == 0) {
            Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        //解析返回的数据
        HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
        //------------------------------------------------------------
        //判断当前请求返回是否 有错误，OK 和 ERR
        Map<String, Object> heads = entity.getHeaders();
        Ioc.getIoc().getLogger().e("ticket:" + data.get("ticket"));
        if ("OK".equals(heads.get(HeaderConst.MYMHOTEL_STATUS))) {
            //存储用户名密码到本地
            Handler_SharedPreferences.WriteSharedPreferences(Constant.HttpUrl.DATA_USER, "ticket", data.get("ticket"));
            isLogin = true; //是否登陆成功
        }
        startActivity();
        //------------------------------------------------------------
    }
    /**
     * 跳转到主页面
     */
    private void startActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                copyMap();
                if (isLogin) {
                    Bundle b = new Bundle();
                    b.putString(FMAPI.ACTIVITY_WHERE, this.getClass().getName());
                    b.putString(FMAPI.ACTIVITY_MAP_ID, Tools.OUTSIDE_MAP_ID);
                    FMAPI.instance().gotoActivity(WelcomeActivity.this, OutdoorMapActivity.class, b);
                    WelcomeActivity.this.finish();
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginAndRegisterFragmentActivity.class));
                    WelcomeActivity.this.finish();
                }
            }
        }, 2000);

    }
}
