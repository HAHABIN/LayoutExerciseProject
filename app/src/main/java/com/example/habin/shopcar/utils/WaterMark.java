package com.example.habin.shopcar.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gq
 * 实现背景水印效果
 *
 * //方式一，使用默认的配置
 * WaterMark.getInstance().show(this, “test”);
 *
 * //方式二，使用自定义配置
 * WaterMark.getInstance()
 * 	.setText("water mark")
 * 	.setTextColor(0x10000000)
 * 	.setTextSize(14)
 * 	.setRotation(-10)
 * 	.show(this);
 */
public class WaterMark {
    private static final String TAG = "WaterMark";

    /** 当前内容不能换行，使用2个变量实现 。后续可以优化*/
    private String mText;//水印内容
    private String mTime;//水印时间

    private int mTextColor;//字体颜色，例如：0x10000000，10表示透明度
    private float mTextSize;//字体大小，单位为sp
    private int mAlpha ;//文字透明度
    private float mRotation;//旋转角度

    private static WaterMark sInstance;//使用单例模式

    @SuppressLint("SimpleDateFormat")
    private WaterMark() {
        mText = "";
        Date date = new Date(System.currentTimeMillis());
        mTime = new SimpleDateFormat().format(date);
        mAlpha = 10;
        mTextColor = 0x10000000;
        mTextSize = 14;
        mRotation = -20;
    }

    public static WaterMark getInstance() {
        if (sInstance == null) {
            synchronized (WaterMark.class) {
                sInstance = new WaterMark();
            }
        }
        return sInstance;
    }

    /** 设置水印文本 */
    public WaterMark setText(String text) {
        mText = text;
        return sInstance;
    }

    /** 设置水印时间 */
    public WaterMark setTime(String time) {
        mTime = time;
        return sInstance;
    }

    /** 设置透明度 */
    public WaterMark setAlpha(int alpha) {
        mAlpha = alpha;
        return sInstance;
    }

    /** 设置字体颜色 */
    public WaterMark setTextColor(int color) {
        mTextColor = color;
        return sInstance;
    }

    /** 设置字体大小 */
    public WaterMark setTextSize(float size) {
        mTextSize = size;
        return sInstance;
    }

    /** 设置旋转角度 */
    public WaterMark setRotation(float degrees) {
        mRotation = degrees;
        return sInstance;
    }

    /** 显示水印，铺满整个页面 */
    public void show(Activity activity) {
        WeakReference<Activity> mActivity  = new WeakReference<>(activity);//使用弱引用，避免内存泄漏
        if(null != mActivity.get()){
            show(mActivity.get(), mText);
        }
    }

    /**
     * 显示水印，铺满整个页面
     *
     * @param activity 活动
     * @param text     水印
     */
    public void show(Activity activity, String text) {
        WeakReference<Activity> mActivity  = new WeakReference<>(activity);//使用弱引用，避免内存泄漏
        if(null == mActivity.get()){
            return;
        }

        setText(text);

        WatermarkDrawable drawable = new WatermarkDrawable();

        ViewGroup rootView = mActivity.get().findViewById(android.R.id.content);
        FrameLayout layout = new FrameLayout(mActivity.get());
        layout.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackground(drawable);
        rootView.addView(layout);
    }

    private class WatermarkDrawable extends Drawable {
        //画笔
        private Paint mPaint;

        private int mHeight;
        private int mWidth;

        private WatermarkDrawable() {
            mPaint = new Paint();
        }

        public WatermarkDrawable(int mHeight, int mWidth) {
            mPaint = new Paint();
            this.mHeight = mHeight;
            this.mWidth = mWidth;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            //获取矩形右侧与x轴原点的位置，换个理解就是右侧边距离y轴的距离
            int width = getBounds().right;
            //获取矩形底部与Y轴原点的位置，换个理解就是底部边距离x轴的距离
            int height = getBounds().bottom;
            //设置颜色
            mPaint.setColor(mTextColor);
            //设置字体大小
            mPaint.setTextSize(spToPx(mTextSize));
            //设置抗拒齿
            mPaint.setAntiAlias(true);
            //根据时间显示长度
            float textWidth = mPaint.measureText(mText+" "+mTime);
            Log.d(TAG,"width = " + width + " || height = " + height + "|| textWidth = " + textWidth);
            //设置Canvas的背景颜色。
            canvas.drawColor(0x00000000);
            //整个背景
//            canvas.rotate(mRotation);//设置画布旋转
            //设置画笔透明度
            mPaint.setAlpha(mAlpha);
            /** 获取中心点*/
            int initHeigh = height/2;
            int initwidth = width/2;

            for (int columnLineCount = 0; columnLineCount < 3; columnLineCount++) {

                Path path = new  Path();
                    //移动画笔 设置起始点（改变后面操作的起始点位置）
                    /**  width-textWidth/2 减去文字一半  从中间开始填  y+200是为了尾部在上面*/
                    path.moveTo(initwidth-textWidth/2,initwidth+columnLineCount*400+200);
                    //绘制线，线的起点就是moveTo  绘制路线:添加起始点到目标点（x，y）构成的直线到path
                    path.lineTo(textWidth/2+initwidth, initwidth+columnLineCount*400);
                    /** 开始画文本
                     * hOffset水平偏移量
                     * vOffset垂直偏移量，实现文本的换行
                     */
                    canvas.drawTextOnPath(mText+" "+mTime,path,0,0,mPaint);
//                    canvas.drawTextOnPath(mTime,path,0,200,mPaint);

            }
            canvas.save();
            //回滚
            canvas.restore();
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
        }

        @Override
        public int getOpacity() {
            //透明，只有绘制的地方才覆盖底下的内容。
            return PixelFormat.TRANSLUCENT;
        }



    }

    /**
     * Value of sp to value of px.
     * spToPx()这个方法是将sp转换成px
     * @param spValue The value of sp.
     * @return value of px
     */
    public static int spToPx(float spValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
