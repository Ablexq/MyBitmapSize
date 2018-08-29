package com.example.administrator.mybitmapsize.util;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Java中的SoftReference即对象的软引用。如果一个对象具有软引用，内存空间足够，垃圾回收器就不会回收它；
 * 如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。
 * 软引用可用来实现内存敏感的高速缓存。使用软引用能防止内存泄露，增强程序的健壮性。
 */
public class SoftReferenceUtil {

    private Map<String, SoftReference<Bitmap>> hashMap = new HashMap<>();

    private static final String TAG = "SoftReferenceUtil";

    private static SoftReferenceUtil softReferenceUtil;

    public static SoftReferenceUtil getInstance() {
        if (softReferenceUtil == null) {
            softReferenceUtil = new SoftReferenceUtil();
        }
        return softReferenceUtil;
    }

    /**
     * 添加
     */
    public synchronized void addBitmap(String key, Bitmap bitmap) {//key可以为url或者resId
        if (key != null && bitmap != null)
            hashMap.put(key, new SoftReference<>(bitmap));
    }

    /**
     * 获取
     */
    public synchronized Bitmap getBitmap(String key) {
        SoftReference<Bitmap> reference = hashMap.get(key);
        if (reference != null && reference.get() != null) {
            return reference.get();
        }
        return null;
    }


}
