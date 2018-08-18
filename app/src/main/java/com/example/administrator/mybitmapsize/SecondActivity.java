package com.example.administrator.mybitmapsize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIv1;
    private int bitmapSize1;
    private int bitmapSize2;
    private int bitmapSize3;
    private int bitmapSize4;
    private int bitmapSize5;
    private int bitmapSize6;
    private int bitmapSize7;
    private int bitmapSize8;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;
    private Bitmap bitmap5;
    private Bitmap bitmap6;
    private Bitmap bitmap7;
    private Bitmap bitmap8;
    private ImageView mIv2;
    private TextView mTvSize1;
    private TextView mTvSize2;
    private TextView mTvSize3;
    private TextView mTvSize4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViews();
        Utils.getDensity(this);
    }

    private void findViews() {
        this.findViewById(R.id.tv1).setOnClickListener(this);
        this.findViewById(R.id.tv2).setOnClickListener(this);
        this.findViewById(R.id.tv3).setOnClickListener(this);
        this.findViewById(R.id.tv4).setOnClickListener(this);
        this.findViewById(R.id.tv5).setOnClickListener(this);
        this.findViewById(R.id.tv6).setOnClickListener(this);
        this.findViewById(R.id.tv7).setOnClickListener(this);
        this.findViewById(R.id.tv8).setOnClickListener(this);
        this.findViewById(R.id.tv_title1).setOnClickListener(this);
        this.findViewById(R.id.tv_title2).setOnClickListener(this);
        mIv1 = ((ImageView) this.findViewById(R.id.iv1));
        mIv2 = ((ImageView) this.findViewById(R.id.iv2));
        mTvSize1 = ((TextView) this.findViewById(R.id.tvm));
        mTvSize2 = ((TextView) this.findViewById(R.id.tvh));
        mTvSize3 = ((TextView) this.findViewById(R.id.tvxh));
        mTvSize4 = ((TextView) this.findViewById(R.id.tvxxh));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.discoverxxh1);
                mIv1.setImageBitmap(bitmap1);
                mIv2.setImageBitmap(bitmap1);
                break;
            case R.id.tv2:
                bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.discoverxxh2);
                mIv1.setImageBitmap(bitmap2);
                mIv2.setImageBitmap(bitmap2);
                break;
            case R.id.tv3:
                bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.discoverxxh3);
                mIv1.setImageBitmap(bitmap3);
                mIv2.setImageBitmap(bitmap3);
                break;
            case R.id.tv4:
                bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.discoverxxh4);
                mIv1.setImageBitmap(bitmap4);
                mIv2.setImageBitmap(bitmap4);
                break;

            case R.id.tv5:
                bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.discoverm1);
                mIv1.setImageBitmap(bitmap5);
                mIv2.setImageBitmap(bitmap5);
                break;
            case R.id.tv6:
                bitmap6 = BitmapFactory.decodeResource(getResources(), R.drawable.discoverm2);
                mIv1.setImageBitmap(bitmap6);
                mIv2.setImageBitmap(bitmap6);
                break;
            case R.id.tv7:
                bitmap7 = BitmapFactory.decodeResource(getResources(), R.drawable.discoverm3);
                mIv1.setImageBitmap(bitmap7);
                mIv2.setImageBitmap(bitmap7);
                break;
            case R.id.tv8:
                bitmap8 = BitmapFactory.decodeResource(getResources(), R.drawable.discoverm4);
                mIv1.setImageBitmap(bitmap8);
                mIv2.setImageBitmap(bitmap8);
                break;

            case R.id.tv_title2:
                bitmapSize5 = Utils.getBitmapSize(bitmap5);
                bitmapSize6 = Utils.getBitmapSize(bitmap6);
                bitmapSize7 = Utils.getBitmapSize(bitmap7);
                bitmapSize8 = Utils.getBitmapSize(bitmap8);
                if (bitmapSize5 != 0) {
                    float i6 = ((float) bitmapSize5) / ((float) bitmapSize6);
                    float i7 = ((float) bitmapSize5) / ((float) bitmapSize7);
                    float i8 = ((float) bitmapSize5) / ((float) bitmapSize8);

                    float width5 = bitmap5.getWidth();
                    float width6 = bitmap6.getWidth();
                    float width7 = bitmap7.getWidth();
                    float width8 = bitmap8.getWidth();
                    float v6 = width5 / width6;
                    float v7 = width5 / width7;
                    float v8 = width5 / width8;
                    System.out.println("bitmapSize5 / bitmapSize6======" + i6 + " width5 / width6=======" + v6);
                    System.out.println("bitmapSize5 / bitmapSize7======" + i7 + " width5 / width7=======" + v7);
                    System.out.println("bitmapSize5 / bitmapSize8======" + i8 + " width5 / width8=======" + v8);
//                    bitmapSize5 / bitmapSize6======2.25 width5 / width6=======1.5
//                    bitmapSize5 / bitmapSize7======4.0  width5 / width7=======2.0
//                    bitmapSize5 / bitmapSize8======9.0  width5 / width8=======3.0
                    mTvSize1.setText("" + bitmapSize5 / 1024 + "kb" + "\n" + width5 + "px");
                    mTvSize2.setText("" + bitmapSize6 / 1024 + "kb" + "\n" + width6 + "px");
                    mTvSize3.setText("" + bitmapSize7 / 1024 + "kb" + "\n" + width7 + "px");
                    mTvSize4.setText("" + bitmapSize8 / 1024 + "kb" + "\n" + width8 + "px");
                }
                break;

            case R.id.tv_title1:
                bitmapSize1 = Utils.getBitmapSize(bitmap1);
                bitmapSize2 = Utils.getBitmapSize(bitmap2);
                bitmapSize3 = Utils.getBitmapSize(bitmap3);
                bitmapSize4 = Utils.getBitmapSize(bitmap4);
                if (bitmapSize1 != 0) {
                    float i2 = ((float) bitmapSize1) / ((float) bitmapSize2);
                    float i3 = ((float) bitmapSize1) / ((float) bitmapSize3);
                    float i4 = ((float) bitmapSize1) / ((float) bitmapSize4);

                    float width1 = bitmap1.getWidth();
                    float width2 = bitmap2.getWidth();
                    float width3 = bitmap3.getWidth();
                    float width4 = bitmap4.getWidth();
                    float v2 = width1 / width2;
                    float v3 = width1 / width3;
                    float v4 = width1 / width4;
                    System.out.println("bitmapSize1 / bitmapSize2======" + i2 + " width1 / width2=======" + v2);
                    System.out.println("bitmapSize1 / bitmapSize3======" + i3 + " width1 / width3=======" + v3);
                    System.out.println("bitmapSize1 / bitmapSize4======" + i4 + " width1 / width4=======" + v4);
//                    bitmapSize1 / bitmapSize2======2.25      width1 / width2=======1.5
//                    bitmapSize1 / bitmapSize3======3.9714797 width1 / width3=======1.9928571
//                    bitmapSize1 / bitmapSize4======9.0       width1 / width4=======3.0
                    mTvSize1.setText("" + bitmapSize1 / 1024 + "kb" + "\n" + width1 + "px");
                    mTvSize2.setText("" + bitmapSize2 / 1024 + "kb" + "\n" + width2 + "px");
                    mTvSize3.setText("" + bitmapSize3 / 1024 + "kb" + "\n" + width3 + "px");
                    mTvSize4.setText("" + bitmapSize4 / 1024 + "kb" + "\n" + width4 + "px");
                }
                /* v2  = v1 * 1.5 */
                /* v3  = v1 * 2 */
                /* v4  = v1 * 3 */
                /* 宽度变化按照DPI 计算
                    public static final int DENSITY_LOW = 120;
                    public static final int DENSITY_MEDIUM = 160;//默认值
                    public static final int DENSITY_TV = 213;	 //TV专用
                    public static final int DENSITY_HIGH = 240;
                    public static final int DENSITY_260 = 260;
                    public static final int DENSITY_280 = 280;
                    public static final int DENSITY_300 = 300;
                    public static final int DENSITY_XHIGH = 320;
                    public static final int DENSITY_340 = 340;
                    public static final int DENSITY_360 = 360;
                    public static final int DENSITY_400 = 400;
                    public static final int DENSITY_420 = 420;
                    public static final int DENSITY_XXHIGH = 480;
                    public static final int DENSITY_560 = 560;
                    public static final int DENSITY_XXXHIGH = 640;

                    //默认DENSITY_MEDIUM
                    public static final int DENSITY_DEFAULT = DENSITY_MEDIUM;
                 */

                /* i2  = i1 * 1.5*1.5 */
                /* i3  = i1 * 2*2 */
                /* i4  = i1 * 3*3 */
                /* 尺寸按照长*宽  */
                break;
        }
    }
}
