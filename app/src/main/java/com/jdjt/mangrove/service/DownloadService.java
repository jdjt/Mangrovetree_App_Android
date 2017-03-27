package com.jdjt.mangrove.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

/**
 * 检测安装更新文件的助手类
 *
 * @author wmy
 */

public class DownloadService extends Service {

    public DownloadService() {
    }

    Long downloadId = -1L;
    /**
     * 安卓系统下载类
     **/
    DownloadManager manager;

    /**
     * 接收下载完的广播
     **/
    DownloadCompleteReceiver receiver;

    /**
     * 初始化下载器
     **/
    private void initDownManager() {

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        receiver = new DownloadCompleteReceiver();

        //设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(
//                百度音乐
                Uri.parse("http://gdown.baidu.com/data/wisegame/fd84b7f6746f0b18/baiduyinyue_4802.apk"));
//    乐视体育            Uri.parse("http://122.228.237.132/apk.r1.market.hiapk.com/data/upload/apkres/2016/6_12/16/com.lesports.glivesports_040405.apk"));
//                Uri.parse("http://192.168.0.128:8080/app-release.apk"));

        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        down.setTitle("正在下载更新");
        // 下载时，通知栏显示途中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        // 显示下载界面
        down.setVisibleInDownloadsUi(true);
        down.setMimeType("application/vnd.android.package-archive");
        // 设置下载后文件存放的位置
        down.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "update.apk");
        // 将下载请求放入队列
        downloadId = manager.enqueue(down);

        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //判断使用权限
        if (canDownloadState()) {
            // 调用下载
            initDownManager();
        } else {
            Toast.makeText(this, "请开启相应权限！", Toast.LENGTH_LONG).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {

        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);

        super.onDestroy();
    }

    /**
     * 判断当前需要使用权限
     *
     * @return
     */
    private boolean canDownloadState() {
        try {
            int state = this.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Ioc.getIoc().getLogger().e("下载完成的 广播");
            //判断是否下载完成的广播
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Ioc.getIoc().getLogger().e("判断是否下载完成的广播");
                //获取下载的文件id
//                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Ioc.getIoc().getLogger().e("获取下载的文件id =" + downloadId);
                //自动安装apk
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    Uri uriForDownloadedFile = manager.getUriForDownloadedFile(downloadId);
                    Ioc.getIoc().getLogger().e("uri=" + uriForDownloadedFile);
                    installApkNew(uriForDownloadedFile);
                }

                //停止服务并关闭广播
                DownloadService.this.stopSelf();

            }
        }

        //安装apk
        protected void installApkNew(Uri uri) {
            Intent intent = new Intent();
            //执行动作
            intent.setAction(Intent.ACTION_VIEW);
            //执行的数据类型
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            //不加下面这句话是可以的，查考的里面说如果不加上这句的话在apk安装完成之后点击单开会崩溃
            // android.os.Process.killProcess(android.os.Process.myPid());
            startActivity(intent);

        }
    }
}
