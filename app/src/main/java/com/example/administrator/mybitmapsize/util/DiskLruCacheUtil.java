package com.example.administrator.mybitmapsize.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

//参考：LruCache内存缓存与DiskLruCache硬盘缓存完美集合
//https://blog.csdn.net/u012426327/article/details/78688920
//https://www.cnblogs.com/huhx/p/useDiskLruCache.html
public class DiskLruCacheUtil {

    private static final String TAG = "DiskLruCacheUtil";

    private static DiskLruCacheUtil diskLruCacheUtil;
    private DiskLruCache mDiskLruCache = null;

    public static DiskLruCacheUtil getInstance(Context context) {
        if (diskLruCacheUtil == null) {
            diskLruCacheUtil = new DiskLruCacheUtil(context);
        }
        return diskLruCacheUtil;
    }

    private DiskLruCacheUtil(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     open()方法接收四个参数：
     第一个参数：指定的是数据的缓存地址
     第二个参数：指定当前应用程序的版本号
     第三个参数：指定同一个key可以对应多少个缓存文件，基本都是传1
     第四个参数：指定最多可以缓存多少字节的数据
     */


    /*
    * 获取
    * */
    public synchronized Bitmap getBitmap(String key) {
        DiskLruCache.Snapshot snapShot;
        try {
            snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                return BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getCacheSize() {
        return mDiskLruCache.size();
    }

    public void close() {
        /**
         * 这个方法用于将DiskLruCache关闭掉，是和open()方法对应的一个方法。
         * 关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，
         * 通常只应该在Activity的onDestroy()方法中去调用close()方法。
         */
        try {
            mDiskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            mDiskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 移除缓存
    * */
    public synchronized void removeImageCache(String key) {
        if (key != null) {
            try {
                mDiskLruCache.remove(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        //如果sd卡存在并且没有被移除
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
