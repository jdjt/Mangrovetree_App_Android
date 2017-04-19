package com.jdjt.mangrove.receiver;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jdjt.mangrove.activity.DownloadActivity;
import com.jdjt.mangrove.util.Downloader;

import java.io.File;

/**
 * @author wmy
 */

public class ApkInstallReceiver extends BroadcastReceiver {

    private static final String TAG = "ApkInstallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();


        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            DownloadManager downloadManager = (DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE);
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            Cursor cursor =null;
            try {
                 cursor = downloadManager.query(query);
                Log.i(TAG, "onReceive: + " + action + ", downloadId = " + downloadId);
                Log.i(TAG, "cursor: + " + cursor + ", cursor.moveToFirst() = " + cursor.moveToFirst());

                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    int status = cursor.getInt(columnIndex);
                    int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                    int reason = cursor.getInt(columnReason);
                    Log.i("LOG_DEBUG", "STATUS_RUNNING :" + reason);
                    switch (status) {
                        case DownloadManager.STATUS_FAILED:

                            break;
                        case DownloadManager.STATUS_PAUSED:

                            break;
                        case DownloadManager.STATUS_PENDING:

                            break;
                        case DownloadManager.STATUS_RUNNING:

                            break;
                        case DownloadManager.STATUS_SUCCESSFUL:
                            installApk(context, downloadId);

                            break;
                    }

                }
            }finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }else if(DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())){
            //暂时不处理点击事件
//            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//            Intent mintent = new Intent(Intent.ACTION_MAIN);
////            mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//            mintent.addCategory(Intent.CATEGORY_LAUNCHER);
//            mintent.setClass(context, DownloadActivity.class);
//            mintent.putExtra(DownloadManager.EXTRA_DOWNLOAD_ID,downloadId);
//            mintent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式
////            PendingIntent contextIntent = PendingIntent.getActivity(context, 0, mintent, 0);
//            context.startActivity(mintent);
        }
    }

    private void installApk(Context context, long downloadId) {
        DownloadManager downloadManager
                = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (uri != null) {
            File apkFile = queryDownloadedApk(downloadManager, downloadId);
            if (apkFile.exists()) {
                intent.setDataAndType(Uri.fromFile(apkFile),
                        "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } else {
                Log.e(TAG, "installApk: download error");
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {
            Log.e(TAG, "installApk: download error");
        }
    }

    private File queryDownloadedApk(DownloadManager downloadManager, long mDownloadId) {
        File targetApkFile = null;
        if (mDownloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(mDownloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cursor = downloadManager.query(query);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String uriString = cursor.getString(cursor
                            .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cursor.close();
            }
        }
        return targetApkFile;
    }
}
