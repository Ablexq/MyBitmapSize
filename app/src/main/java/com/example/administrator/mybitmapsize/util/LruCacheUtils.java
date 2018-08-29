package com.example.administrator.mybitmapsize.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * 内存控制工具LruCache
 */
public class LruCacheUtils {

    private static final String TAG = "LruCacheUtils";

    private static LruCacheUtils mImageLoader;

    public static LruCacheUtils getInstance() {
        if (mImageLoader == null) {
            mImageLoader = new LruCacheUtils();
        }
        return mImageLoader;
    }

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private static LruCache<String, Bitmap> mMemoryCache;

    private LruCacheUtils() {
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
    }

    /**
     * 添加
     */
    public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {//key可以为url或者resId
        if (mMemoryCache.get(key) == null) {
            if (key != null && bitmap != null) {
                mMemoryCache.put(key, bitmap);
            }
        } else {
            Log.w(TAG, "the res is aready exits");
        }
    }

    /*
    * 获取
    * */
    public synchronized Bitmap getBitmapFromMemoryCache(String key) {
        Bitmap bm = mMemoryCache.get(key);
        if (key != null) {
            return bm;
        }
        return null;
    }

    //移除和清除缓存是必须要做的事，因为图片缓存处理不当就会报内存溢出，所以一定要引起注意。
    /*
    * 清空缓存
    * */
    public static void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                mMemoryCache.evictAll();
            }
            mMemoryCache = null;
        }
    }

    /*
    * 移除缓存
    * */
    public synchronized void removeImageCache(String key) {
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }
}
