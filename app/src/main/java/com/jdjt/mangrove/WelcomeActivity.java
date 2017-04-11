package com.jdjt.mangrove;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
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
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_Network;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_SharedPreferences;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_String;
import com.jdjt.mangrovetreelibray.ioc.handler.Handler_System;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.plug.net.FastHttp;
import com.jdjt.mangrovetreelibray.ioc.plug.net.ResponseEntity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
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
        isNetworkConneted(this);
    }

    /**
     *  判断是否有网络连接
     *  @param context
     *  @return
     */
    public boolean isNetworkConneted(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
        } else {
//        Toast.makeText(this,"网络连接断开，请检查网络设置",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WelcomeActivity.this, LoginAndRegisterFragmentActivity.class));
        }
        return false;
    }
//
//    public boolean isNetworkConnected(Context context) {
//        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//        if (mNetworkInfo != null && mNetworkInfo.isAvailable()){   //判断网络连接是否打开
//            return  mNetworkInfo.isConnected();
//        }
//        return false;
//    }

    /**
     * 初始化 图片加载配置
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        //缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "image/cache");
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_load_image) // resource or drawable
                .showImageForEmptyUri(R.drawable.default_load_image) // resource or drawable
                .showImageOnFail(R.drawable.default_load_image) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(false) // default
                .cacheOnDisk(false) // default
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(5) //线程池内线程的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // SD卡缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)
                // 由原先的discCache -> diskCache
//                .diskCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);
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
        isFirst();
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
     * 初始化登陆并加载 框架
     */
    private void login() {
        if(!Handler_Network.isNetworkAvailable(this)){
            Toast.makeText(this, "当前没有可用网络", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WelcomeActivity.this, LoginAndRegisterFragmentActivity.class));
            WelcomeActivity.this.finish();
            return;
        }
        Ioc.getIoc().init(MangrovetreeApplication.instance);
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getAppExceptionHandler(this));

        initImageLoader(getApplicationContext());
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
        Ioc.getIoc().getLogger().e("转换像素：" + Handler_System.px2dip(132));
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
        }, 500);

    }


    /**
    * @method 验证首次安装  亚楠
    */
    private void isFirst(){
        SharedPreferences sp = this.getSharedPreferences("is",MODE_PRIVATE);
        boolean is_first = sp.getBoolean("is_first",true);
        SharedPreferences.Editor editor = sp.edit();
        if(is_first){//如果是第一次
            editor.putBoolean("is_first",false);
            editor.commit();
            deleteAllFiles(new File(SDPATH));// 递规的方式删除文件夹
        }else {//不是第一次
//            Toast.makeText(this,"第n次",Toast.LENGTH_SHORT).show();
        }
    }

    /**
    *  删除文件夹和文件夹里面的文件
    */
    static  String SDPATH = ResourcesUtils.getSDPath()
            + "/fm_drpeng";
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
}
