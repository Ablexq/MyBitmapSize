package com.example.administrator.mybitmapsize;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) this.findViewById(R.id.image);
        imageView.setOnClickListener(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);

        Utils.getBitmapSize(bitmap);
        Utils.getDensity(this);
        Utils.printBitmapSize(imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                break;
        }
    }
}
