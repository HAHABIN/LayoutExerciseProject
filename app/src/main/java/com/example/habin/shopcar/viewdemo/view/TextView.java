package com.example.habin.shopcar.viewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.habin.shopcar.R;

/**
 * <自定义的TextView> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/7/25
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TextView extends View {

    private String mText;
    //默认字体大小
    private int mTextSize = 15;
    //默认颜色
    private int mTextColor = Color.BLACK;
    //画笔
    private Paint mPaint;
    /**
     *构造函数会在代码里new的时候调用
     *TextView tv = new TextView(this);
     **/
    public TextView(Context context) {
//        super(context);
        /** 无论怎么调用都会到第二个构造方法去*/
        this(context,null);
    }
    /**
     * 在布局layout中使用
     *
     <com.example.habin.shopcar.viewdemo.view.TextView
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"/>
     * */
    public TextView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs,0);
    }

    /**
     * 在布局layout中使用（调用）但会有style
     * */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属`性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);

        mText = array.getString(R.styleable.TextView_DemoText);
        /** 第一个参数获取属性，第二个添加默认属性*/
        mTextColor = array.getColor(R.styleable.TextView_DemoTextColor,mTextColor);
        /** 可以看TextView源码 此处是15 还是15px 还是15sp*/
        mTextSize = array.getDimensionPixelSize(R.styleable.TextView_DemoTextSize,spToPx(mTextSize));

        //回收
        array.recycle();

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置字体大小
        mPaint.setTextSize(mTextSize);
        //设置画笔颜色
        mPaint.setColor(mTextColor);
    }

    private int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp
                ,getResources().getDisplayMetrics());
    }


    /**
     * 自定义view的测量方法
     *
     *
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的宽高又这个方法指定
        //指定控件的宽高 需要测量
        //获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //1.确定的值，这个时候不需要计算 给多少是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);


        //2.给的是wrap_content 需要计算
        if (widthMode == MeasureSpec.AT_MOST){
            //计算的宽度 与 字体的长度有关 与字体大小有关 用画笔来测量
            Rect bounds = new Rect();
            //测量获取文本的Rect
            mPaint.getTextBounds(mText,0 ,mText.length(),bounds);
            width = bounds.width() + getPaddingLeft() + getPaddingRight();
        }
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST){
            //计算的宽度 与 字体的长度有关 与字体大小有关 用画笔来测量
            Rect bounds = new Rect();
            //获取文本的Rect
            mPaint.getTextBounds(mText,0 ,mText.length(),bounds);
            height = bounds.height() + getPaddingBottom() + getPaddingTop();
        }


        //设置控件宽高
        setMeasuredDimension(width,height);
    }

    /** 绘制*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**画文字
         * x 就是开始的位置
         * y 基线 baseLine  相当与现实中 4线谱写字  第三条线就是基线
         * dy 代表的是：高度的一半到baseLine的距离、
         * 绘制区间 fontMetricsInt.bottom-fontMetricsInt.top
         *  top bottom的值代表
         *  top 是baseLine到文字顶部的距离 所以是负数 以baseLine起点为原点
         *  bottom 是baseLine到文字底部的距离 所以是正数
         * */
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;
        int x = getPaddingLeft();
        /**
         * (x，y)所代表的位置是所画图形对应的矩形的左上角点。但在drawText中是非常例外的！
         * */
        canvas.drawText(mText,x,baseLine,mPaint);
    }
}
