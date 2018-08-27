package com.example.administrator.mybitmapsize;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.tv_default).setOnClickListener(this);
        this.findViewById(R.id.tv_good).setOnClickListener(this);

        imageView = (ImageView) this.findViewById(R.id.image);
        imageView.setOnClickListener(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);

        BitmapUtils.getBitmapSize(bitmap);
        BitmapUtils.getDensity(this);
        BitmapUtils.printBitmapSize(imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                break;

            case R.id.tv_default:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);
                System.out.println("111default===================" + BitmapUtils.getBitmapSize(bitmap));
                imageView.setImageBitmap(bitmap);
                break;

            case R.id.tv_good:
                Bitmap fitSampleBitmap = BitmapUtils.getFitSampleBitmap(getResources(), R.drawable.pic1, imageView.getWidth(), imageView.getHeight());
                System.out.println("111goods===================" + BitmapUtils.getBitmapSize(fitSampleBitmap));
                imageView.setImageBitmap(fitSampleBitmap);
                break;
        }
    }
}
