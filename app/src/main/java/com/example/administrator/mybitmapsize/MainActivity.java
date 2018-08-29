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
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flash_bg1);
                BitmapUtils.getBitmapSize(bitmap);
                imageView.setImageBitmap(bitmap);
                break;

            case R.id.tv_good:
                Bitmap fitSampleBitmap = BitmapUtils.compressSample(getResources(), R.drawable.flash_bg1, imageView.getWidth(), imageView.getHeight());
                BitmapUtils.getBitmapSize(fitSampleBitmap);
                imageView.setImageBitmap(fitSampleBitmap);
                break;

            case R.id.tv_good2:
                Bitmap bitmap1 = BitmapDecodeUtil.decodeBitmap(this, R.drawable.flash_bg1);
                SoftReference<Bitmap> bitmapSoftReference = new SoftReference<>(bitmap1);/*软引用，内存不足就回收*/
                BitmapUtils.getBitmapSize(bitmapSoftReference.get());
                imageView.setImageBitmap(bitmapSoftReference.get());
                break;
        }
    }
}

