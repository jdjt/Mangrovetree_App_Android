package com.jdjt.mangrove.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;


import com.jdjt.mangrove.R;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.io.File;

/**
 * 文件下载类
 *
 * @author wmy
 */
public class Downloader {

    private static final String TAG = Downloader.class.getSimpleName();

    private DownloadManager mDownloadManager;
    private Context mContext;

    private static final String SP_NAME = "sp_download";
    private static final String KEY_DOWNLOAD_ID = "download_id";
    private static final int PROGRESS_UPDATE_DURATION = 200;

    private long mDownloadId = -1;

    public Downloader(Context context) {
        mContext = context;
        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * 启动下载
     * @param downloadUrl
     * @return
     */
    public long startDownload(String downloadUrl) {
        Log.i(TAG, "startDownload: downloadUrl = " + downloadUrl);
        if (TextUtils.isEmpty(downloadUrl)) {
            Log.e(TAG, "startDownload: downloadUrl is null");
            return -1;
        }
        long lastSavedDownloadId = getDownloadedId();
        if (lastSavedDownloadId != -1) {
            int downloadStatus = getDownloadedStatus(lastSavedDownloadId);
            if (downloadStatus != DownloadManager.STATUS_SUCCESSFUL) {
                return download(downloadUrl);
            }
            Uri uri = getDownloadedApkFileUri(lastSavedDownloadId);
            if (uri != null) {
                if (isVersionNew(uri)) {
                    if (mOnDownloadProgressChangeListener != null) {
                        mOnDownloadProgressChangeListener.onDownloadProgressChange(100, 100);
                    }
                    return installApk(mContext, lastSavedDownloadId);
                } else {
                    mDownloadManager.remove(lastSavedDownloadId);
                }
            } else {
                return download(downloadUrl);
            }
        }
        return download(downloadUrl);
    }

    /**
     * 获取下载状态
     * @param downloadId
     * @return
     */
    private int getDownloadedStatus(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = mDownloadManager.query(query);
        int status = -100;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
            cursor.close();
        }
        Log.i(TAG, "getDownloadStatus: status " + status);

        return status;
    }

    /**
     * 下载文件
     * @param downloadUrl
     * @return
     */
    private long download(String downloadUrl) {
        //创建下载队列
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(downloadUrl));
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        req.setAllowedOverRoaming(false);
        Ioc.getIoc().getLogger().i("保存地址:" + Environment.DIRECTORY_DOWNLOADS);
        if (hasSDCard()) {
            req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                    getApplicationLabel(mContext) + ".apk");
        } else {
            req.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS,
                    getApplicationLabel(mContext) + ".apk");
        }
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        req.setTitle(getApplicationLabel(mContext));
        req.setDescription(mContext.getString(R.string.update_desc));
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeType = mimeTypeMap
                .getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(downloadUrl));
        req.setMimeType(mimeType);
        req.setVisibleInDownloadsUi(true);
        long downloadId = mDownloadManager.enqueue(req);
        mDownloadId = downloadId;
        Log.i(TAG, "download: downloadId = " + downloadId);
        saveDownloadId(downloadId);
        if (mOnDownloadProgressChangeListener != null) {
            startProgressTask();
        }
        return downloadId;
    }

    /**
     * 判断当前是否有sd 卡
     * @return
     */
    private boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取新版
     * @param uri
     * @return
     */
    private boolean isVersionNew(Uri uri) {
        PackageInfo localPackageInfo = getPackageInfo(mContext);
        if (localPackageInfo == null) {
            return false;
        }
        PackageInfo newPackageInfo = getPackageInfo(mContext, uri.getPath());
        return newPackageInfo != null && newPackageInfo.versionCode > localPackageInfo.versionCode;
    }

    /**
     * 获取下载中任务 id
     * @return
     */
    private long getDownloadedId() {
        SharedPreferences spf = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return spf.getLong(KEY_DOWNLOAD_ID, -1);
    }

    /**
     * 保存下载中id
     * @param downloadId
     */
    private void saveDownloadId(long downloadId) {
        SharedPreferences spf = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putLong(KEY_DOWNLOAD_ID, downloadId);
        editor.apply();
    }

    private PackageInfo getPackageInfo(Context context) {
        PackageInfo info;
        try {
            PackageManager pm = context.getPackageManager();
            info = pm.getPackageInfo(context.getPackageName(), 0);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用名称
     * @param context
     * @return
     */
    private String getApplicationLabel(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return (String) pm.getApplicationLabel(pm
                    .getApplicationInfo(context.getPackageName(), 0));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "update";
        }
    }

    /**
     * 获取报信息
     * @param context
     * @param path
     * @return
     */
    private PackageInfo getPackageInfo(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info;
        }
        return null;
    }

    /**
     * 安装应用
     * @param context
     * @param downloadId
     * @return
     */
    private long installApk(Context context, long downloadId) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_DEFAULT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        File apkFile = queryDownloadedApk(downloadId);
        if (apkFile != null && apkFile.exists()) {
            intent.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {
            Log.e(TAG, "installApk: download error");
        }
        return downloadId;
    }

    /**
     * 查询下载apk
     * @param downloadId
     * @return
     */
    private File queryDownloadedApk(long downloadId) {
        File targetApkFile = null;
        if (downloadId != -1) {
            Uri fileUri = getDownloadedApkFileUri(downloadId);
            if (fileUri != null) {
                targetApkFile = new File(fileUri.getPath());
            }
        }
        return targetApkFile;
    }

    /**
     * 获取下载apk 的保存地址
     * @param downloadId
     * @return
     */
    public Uri getDownloadedApkFileUri(long downloadId) {
        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cursor = mDownloadManager.query(query);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String uriString = cursor.getString(cursor
                            .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        return Uri.parse(uriString);
                    }
                }
                cursor.close();
            }
        }
        return null;
    }

    public interface OnDownloadProgressChangeListener {
        void onDownloadProgressChange(long downloadSizeSoFar, long totalSize);
    }

    /**
     * 设置下载监听
     * @param l
     */
    public void setOnDownloadProgressChangeListener(OnDownloadProgressChangeListener l) {
        mOnDownloadProgressChangeListener = l;
    }

    private OnDownloadProgressChangeListener mOnDownloadProgressChangeListener;

    private Handler mHandler = new Handler();

    private Runnable mProgressUpdateTask = new Runnable() {
        @Override
        public void run() {
            onGetDownloadSize(mDownloadId);
        }
    };

    private void onGetDownloadSize(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = mDownloadManager.query(query);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                long totalSize = cursor.getLong(cursor
                        .getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                long downloadSize = cursor.getLong(cursor
                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                Ioc.getIoc().getLogger().i("download totalSize:" + totalSize);
                Ioc.getIoc().getLogger().i("download downloadSize: " + downloadSize);
                if (mOnDownloadProgressChangeListener != null && totalSize > 0) {
                    mOnDownloadProgressChangeListener
                            .onDownloadProgressChange(downloadSize, totalSize);
                }
                if (downloadSize == totalSize) {
                    stopProgressTask();
                } else {
                    mHandler.postDelayed(mProgressUpdateTask, PROGRESS_UPDATE_DURATION);
                }
            }
            cursor.close();
        }

    }

    /**
     * 停止任务队列
     */
    public void stopProgressTask() {
        mHandler.removeCallbacks(mProgressUpdateTask);
    }

    /**
     * 启动任务队列
     */
    public void startProgressTask() {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(mDownloadId);
        Cursor cursor = mDownloadManager.query(query);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                long totalSize = cursor.getLong(cursor
                        .getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                long downloadSize = cursor.getLong(cursor
                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));


                Ioc.getIoc().getLogger().i("startProgressTask download totalSize:" + totalSize);
                Ioc.getIoc().getLogger().i("startProgressTask download downloadSize: " + downloadSize);
                if (downloadSize == totalSize) {
                    stopProgressTask();
                } else {
                    mHandler.post(mProgressUpdateTask);
                }
            }
            cursor.close();
        }
    }

}
