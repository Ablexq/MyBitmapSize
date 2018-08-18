package com.example.administrator.mybitmapsize;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by Administrator on 18/8/2018.
 */

public class Utils {

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


}
