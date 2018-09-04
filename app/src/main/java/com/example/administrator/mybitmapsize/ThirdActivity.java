package com.example.administrator.mybitmapsize;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.mybitmapsize.util.BitmapUtils;


public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView textView1 = (TextView) this.findViewById(R.id.tv1_third);
        TextView textView2 = (TextView) this.findViewById(R.id.tv2_third);
        TextView textView3 = (TextView) this.findViewById(R.id.tv3_third);
        imageView1 = (ImageView) this.findViewById(R.id.iv1_third);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);

        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        System.out.println("maxMemory======================" + (maxMemory / 1024 / 1024) + " Mb");
        System.out.println("freeMemory======================" + (freeMemory / 1024 / 1024) + " Mb");
        System.out.println("totalMemory======================" + (totalMemory / 1024 / 1024) + " Mb");
        System.out.println("已使用======================" + ((totalMemory - freeMemory) / 1024 / 1024) + " Mb");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//内存占用与图片的实际显示尺寸没有关系。与像素、一像素所占内存和所放置的文件夹有关系
            case R.id.tv1_third://同一张图片放在xh/xxh，在xxh的手机上显示
            {
                Bitmap bitmapxx = BitmapFactory.decodeResource(getResources(), R.drawable.start_pic_xx);
                int bitmapSizexx = BitmapUtils.getBitmapSize(bitmapxx);

                Bitmap bitmapx = BitmapFactory.decodeResource(getResources(), R.drawable.start_pic_x);
                int bitmapSizex = BitmapUtils.getBitmapSize(bitmapx);

                //图片24位，所以一像素所占内存=24/8=3byte
                System.out.println("bitmapSizex===========================" + bitmapSizex / 1024 / 1024);//14M
                //（1242*1.5）*（1786*1.5）*3/1024/1024=14.279M
                System.out.println("bitmapSizexx==========================" + bitmapSizexx / 1024 / 1024);//6M
                // 1242*1786*3/1024/1024=6.346M
            }
            break;

            case R.id.tv2_third: //同等宽高像素，位数，不同file大小,bitmap却相同，说明bitmap所占内存与file大小无关
            {
                Bitmap bitmapxx = BitmapFactory.decodeResource(getResources(), R.drawable.start_pic_xx);
                int bitmapSizexx = BitmapUtils.getBitmapSize(bitmapxx);

                Bitmap bitmapsmaller = BitmapFactory.decodeResource(getResources(), R.drawable.start_pic_smaller);
                int bitmapSizesmaller = BitmapUtils.getBitmapSize(bitmapsmaller);

                //图片24位，所以一像素所占内存=24/8=3byte
                System.out.println("bitmapSizesmaller===========================" + bitmapSizesmaller / 1024 / 1024);//6M
                //（1242*1.5）*（1786*1.5）*3/1024/1024=14.279M
                System.out.println("bitmapSizexx==========================" + bitmapSizexx / 1024 / 1024);//6M
                // 1242*1786*3/1024/1024=6.346M
            }
            break;

            case R.id.tv3_third:
                //setImageResource/setBackgroundResource/setImageBitmap/直接xml设置src/直接xml设置background：设置图片所占内存与设置的大小无关，且强制回收回收不掉
                //xml直接设置比set方法所占内存少
//setImageResource:占用内存如下：
//                20dp*20dp 占用55.34MB
//                200dp*200dp 占用55.39MB
//                match_parent 占用56.1MB
//                所以设置图片src占用内存与设置尺寸无关
//setBackgroundResource:占用内存如下：
//                20dp*20dp 占用56.33MB
//                200dp*200dp 占用55.37MB
//                match_parent 占用55.36MB
//setImageBitmap:占用内存如下：
//                20dp*20dp 占用55.35MB
//                200dp*200dp 占用55.34MB
//                match_parent 占用55.43MB
//直接设置src：
//                20dp*20dp 占用49.91MB
//                200dp*200dp 占用49.95MB
//                match_parent 占用49.89MB
//直接设置background：
//                20dp*20dp 占用49.79MB
//                200dp*200dp 占用49.91MB
//                match_parent 占用49.83MB
//glide设置图片：
//                20dp*20dp 占用49.89MB
//                200dp*200dp 占用49.98MB
//                match_parent 占用55.88MB
            {
//                imageView1.setImageResource(R.drawable.start_pic_xx);

//                imageView1.setBackgroundResource(R.drawable.start_pic_xx);

//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.start_pic_xx);
//                imageView1.setImageBitmap(bitmap);

                Glide.with(this)
                        .load(R.drawable.start_pic_xx)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .dontAnimate()
                        .crossFade()
//                        .override(((int) dp2px(200)), ((int) dp2px(200)))//相当于压缩图片，从像素（占用内存并未减少，不管20,200还是其他）
                        .into(imageView1);
            }
            break;
        }
    }

    private static float dp2px(float dp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
