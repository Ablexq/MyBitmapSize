package com.example.administrator.mybitmapsize;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by Administrator on 18/8/2018.
 */

public class BitmapUtils {

    public static int getBitmapSize(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        int bitmapSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//api19以上
            bitmapSize = bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//api12以上
            bitmapSize = bitmap.getByteCount();
        } else {//其他api
            bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
        }

        System.out.println("bitmapSize========================" + bitmapSize + "byte");
        System.out.println("bitmapSize========================" + bitmapSize / 1024 + "kb");
        System.out.println("bitmapSize========================" + bitmapSize / 1024 / 1024 + "Mb");

        return bitmapSize;
    }


    public static long getMaxMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        System.out.println("maxMemory======================" + (maxMemory / 1024 / 1024) + " Mb");
        System.out.println("freeMemory======================" + (freeMemory / 1024 / 1024) + " Mb");
        System.out.println("totalMemory======================" + (totalMemory / 1024 / 1024) + " Mb");
        System.out.println("已使用======================" + ((totalMemory - freeMemory) / 1024 / 1024) + " Mb");
        return maxMemory / 1024 / 1024;
    }


    public static void getDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        System.out.println("=====Density is " + displayMetrics.density);
        System.out.println("=====densityDpi is " + displayMetrics.densityDpi);
        System.out.println("=====height " + displayMetrics.heightPixels);
        System.out.println("=====width " + displayMetrics.widthPixels);
    }

    public static void printBitmapSize(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            //Bitmap.Config.ARGB_8888 每个像素占用4字节
            int bitmapSize = 4 * height * width;
            System.out.println("=====printBitmapSize = width=" + width + ",height=" + height);
            System.out.println("=====printBitmapSize = " + bitmapSize + "byte");
            System.out.println("=====printBitmapSize = " + +bitmapSize / 1024 + "Kb");
            System.out.println("=====printBitmapSize = " + +bitmapSize / 1024 / 1024 + "Mb");
        }
    }

/* ============================================================================================= */

    public static int getFitInSampleSize(int reqWidth, int reqHeight, BitmapFactory.Options options) {
        int inSampleSize = 1;
        if (options.outWidth > reqWidth || options.outHeight > reqHeight) {
            int widthRatio = Math.round((float) options.outWidth / (float) reqWidth);
            int heightRatio = Math.round((float) options.outHeight / (float) reqHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /* 本地图片 */
    public static Bitmap getFitSampleBitmap(String file_path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file_path, options);
        options.inSampleSize = getFitInSampleSize(width, height, options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file_path, options);
    }

    /* 资源图片 */
    public static Bitmap getFitSampleBitmap(Resources resources, int resId, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = getFitInSampleSize(width, height, options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

}
