
package com.example.habin.shopcar.viewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.habin.shopcar.R;

/**
 * <文字变色 > <功能详细描述>
 *
 * @author HABIN
 * @version 2020/7/28
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ColorTrackTextView extends android.support.v7.widget.AppCompatTextView {


    private String mText;
    //默认字体大小
    private int mTextSize = 16;
    //默认颜色
    private int mTextColor = Color.BLACK;
    //改变颜色
    private int mTextChangeColor = Color.RED;

    //当前进度
    private float mCurrentProgress = 0.0f;

    private Paint mDynamicPaint;
    /** 焦点位置颜色*/
    private Paint mChangePaint;

    /** 实现不同朝向*/
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction{
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }
    public ColorTrackTextView(Context context) {
        this(context,null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context,attrs);

    }

    private void initPaint(Context context, AttributeSet attrs) {

        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);

        mTextColor = array.getColor(R.styleable.ColorTrackTextView_DynamicTextColor,mTextColor);
        mTextChangeColor = array.getColor(R.styleable.ColorTrackTextView_ChangeTextColor,mTextChangeColor);

        array.recycle();

        mChangePaint =  getPaintByColor(mTextChangeColor);
        mDynamicPaint =  getPaintByColor(mTextColor);
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        //设置颜色
        paint.setColor(color);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置防抖动
        paint.setDither(true);
        //设置字体大小
        paint.setTextSize(getTextSize());

        return paint;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //注释系统TextView 自己画
//        super.onDraw(canvas);
        //根据进度将中间值算出来
        int middle = (int) (mCurrentProgress*getWidth());

        if (mDirection  == Direction.LEFT_TO_RIGHT){
            /** 绘制前半段 -------------------------*/
            drawText(canvas,mChangePaint,0,middle);
            /** 绘制后半段*/
            drawText(canvas,mDynamicPaint,middle,getWidth());
        } else {
            /** 绘制前半段 -------------------------*/
            drawText(canvas,mChangePaint,0,getWidth()-middle);
            /** 绘制后半段*/
            drawText(canvas,mDynamicPaint,getWidth()-middle,getWidth());
        }

    }

    /**
     * 绘制Text
     * @param canvas
     * @param paint
     * @param start
     * @param end
     * */
    private void drawText(Canvas canvas,Paint paint,int start,int end){
        canvas.save();
        //裁剪
        canvas.clipRect(start,0,end,getHeight());
        String text = getText().toString();
        Rect bounds = new Rect();
        //获取字体宽度
        paint.getTextBounds(text,0,text.length(),bounds);
        //开始位置
        int x = getWidth() / 2 - bounds.width() / 2;
        //基线
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(text,x,baseLine,paint);
        //释放
        canvas.restore();
    }

    public void setDirection(Direction direction){
        this.mDirection = direction;
    }

    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }
}
