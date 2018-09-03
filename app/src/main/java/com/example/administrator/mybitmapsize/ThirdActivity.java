package com.example.administrator.mybitmapsize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mybitmapsize.util.BitmapUtils;


public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView textView1 = (TextView) this.findViewById(R.id.tv1_third);
        TextView textView2 = (TextView) this.findViewById(R.id.tv2_third);
        TextView textView3 = (TextView) this.findViewById(R.id.tv3_third);
        ImageView imageView1 = (ImageView) this.findViewById(R.id.iv1_third);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);

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
        }
    }
}
