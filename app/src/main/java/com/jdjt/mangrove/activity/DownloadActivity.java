package com.jdjt.mangrove.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jdjt.mangrove.R;
import com.jdjt.mangrove.base.CommonActivity;
import com.jdjt.mangrove.util.Downloader;
import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.InPLayer;
import com.jdjt.mangrovetreelibray.ioc.annotation.Init;

@InLayer(value = R.layout.activity_download, parent = R.id.center_common)
public class DownloadActivity extends CommonActivity {

    DownloadManager downloadManager=null;
    DownloadChangeObserver downloadObserver=null;
    long downloadId;
    @Init
    private void init(){
        Downloader downloader =new Downloader(this);
         downloadManager = (DownloadManager)this.getSystemService(this.DOWNLOAD_SERVICE);
         downloadId = getIntent().getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//        DownloadManager.Query query = new DownloadManager.Query();
//        query.setFilterById(downloadId);
//        Cursor cursor = downloadManager.query(query);
//        Log.i("LOG_DEBUG","downloadId :"+downloadId);
//        if(cursor != null && cursor.moveToFirst())
//        {
//            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
//            int status = cursor.getInt(columnIndex);
//            int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
//            int reason = cursor.getInt(columnReason);
//            Log.i("LOG_DEBUG","columnIndex :"+columnIndex);
//            Log.d("LOG_DEBUG","status :"+status);
//            Log.e("LOG_DEBUG","columnReason :"+columnReason);
//            Log.v("LOG_DEBUG","reason :"+reason);
//        }
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadObserver = new DownloadChangeObserver(null);
        getContentResolver().registerContentObserver(downloader.getDownloadedApkFileUri(downloadId), true, downloadObserver);
    }

    class DownloadChangeObserver extends ContentObserver {


        public DownloadChangeObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }


        @Override
        public void onChange(boolean selfChange) {
            queryDownloadStatus();
        }


    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
            Log.v("tag", ""+intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
            queryDownloadStatus();
        }
    };

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if(c!=null&&c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

            int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
            int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
            int fileSizeIdx =
                    c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIdx =
                    c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            String title = c.getString(titleIdx);
            int fileSize = c.getInt(fileSizeIdx);
            int bytesDL = c.getInt(bytesDLIdx);

            // Translate the pause reason to friendly text.
            int reason = c.getInt(reasonIdx);
            StringBuilder sb = new StringBuilder();
            sb.append(title).append("\n");
            sb.append("Downloaded ").append(bytesDL).append(" / " ).append(fileSize);

            // Display the status
            Log.d("tag", sb.toString());
            switch(status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("tag", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.v("tag", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    Log.v("tag", "STATUS_RUNNING");


                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
                    Log.v("tag", "下载完成");
//                  dowanloadmanager.remove(lastDownloadId);
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    Log.v("tag", "STATUS_FAILED");
                    downloadManager.remove(downloadId);
                    break;
            }
        }
    }
}
