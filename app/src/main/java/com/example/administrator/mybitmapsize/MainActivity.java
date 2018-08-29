package com.example.administrator.mybitmapsize;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.mybitmapsize.util.BitmapDecodeUtil;
import com.example.administrator.mybitmapsize.util.BitmapUtils;

import java.lang.ref.SoftReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.tv_default).setOnClickListener(this);
        this.findViewById(R.id.tv_good).setOnClickListener(this);
        this.findViewById(R.id.tv_good2).setOnClickListener(this);

        imageView = (ImageView) this.findViewById(R.id.image);
        imageView.setOnClickListener(this);

//        for (int i = 0; i < 100; i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);
//        }


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);
        BitmapUtils.getBitmapSize(bitmap);
        BitmapUtils.getDensity(this);
        BitmapUtils.printBitmapSize(imageView);

        BitmapUtils.getMaxMemory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                break;

            case R.id.tv_default:
                //质量压缩：
                // png压缩成jpg,透明部分会变黑,
                // jpg图片压缩，但图片大小=宽px*高px*色彩模式单位像素所占内存,所以未变
//                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);//jpg
                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_update);//png
                BitmapUtils.getBitmapSize(bitmap1);
                Bitmap bitmap2 = BitmapUtils.compressQuality(bitmap1);
                BitmapUtils.getBitmapSize(bitmap2);
                imageView.setImageBitmap(bitmap2);
                break;

            case R.id.tv_good:
                //色彩模式压缩：
                // PNG格式 有8位、24位、32位三种形式，不能压缩RGB_565（16位），所以压缩无效
                // jpg格式 由ARGB8888调整为GRB565内存占用减少一半
//                Bitmap bitmap3 = BitmapUtils.compressColorMode(getResources(), R.drawable.pic1);//jpg
                Bitmap bitmap3 = BitmapUtils.compressColorMode(getResources(), R.drawable.bg_update);//png
                Bitmap.Config config2 = bitmap3.getConfig();
                System.out.println("config2=========" + config2);
                BitmapUtils.getBitmapSize(bitmap3);
                imageView.setImageBitmap(bitmap3);
                break;

            case R.id.tv_good2:
                //二次采样压缩：尺寸掌握不好，压缩太厉害，可能会变模糊
//                Bitmap bitmap4 = BitmapUtils.compressSample(getResources(), R.drawable.pic1, 100, 100);//jpg
                Bitmap bitmap4 = BitmapUtils.compressSample(getResources(), R.drawable.bg_update, 100, 100);//png
                BitmapUtils.getBitmapSize(bitmap4);
                imageView.setImageBitmap(bitmap4);
                break;
        }
    }
}
