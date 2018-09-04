package com.example.administrator.mybitmapsize.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


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

/* ======================================二次采样 压缩================================================ */

    public static int getFitInSampleSize(int reqWidth, int reqHeight, BitmapFactory.Options options) {
        int inSampleSize = 1;
        if (options.outWidth > reqWidth || options.outHeight > reqHeight) {
            int widthRatio = Math.round((float) options.outWidth / (float) reqWidth);
            int heightRatio = Math.round((float) options.outHeight / (float) reqHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /* 本地图片 ：二次采样 （分辨率改变）*/
    public static Bitmap compressSample(String file_path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file_path, options);
        options.inSampleSize = getFitInSampleSize(width, height, options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file_path, options);
    }

    /* 资源图片 : 二次采样 （分辨率改变）*/
    public static Bitmap compressSample(Resources resources, int resId, int width, int height) {//宽*高*色彩模式=bitmap大小 ，二次采样宽高均变化则bitmap大小缩小很明显
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = getFitInSampleSize(width, height, options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /* ====================================================================================== */

    /* 资源图片 : 色彩模式*/
    public static Bitmap compressColorMode(Resources resources, int resId) {//ARGB_8888切换到RGB_565即宽*高*色彩模式=bitmap大小减少一半
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /* ==================================== 质量压缩 ============================================= */

    //质量压缩法:使图片质量降低（分辨率不变）可以大幅度地减小体积，而且质量的差异肉眼看上去并不明显（分辨率不变即宽*高*色彩模式=bitmap大小未变）
    public static Bitmap compressQuality(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        System.out.println("t============"+(baos.toByteArray().length / 1024));
        while (baos.toByteArray().length / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /* =============================================================== */

    //图片按比例大小压缩方法（根据路径获取图片并压缩）
    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressQuality(bitmap);//压缩好比例大小后再进行质量压缩
    }

    //图片按比例大小压缩方法（根据Bitmap图片压缩）
    public static Bitmap comp(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressQuality(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /* ============================================= */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

}
