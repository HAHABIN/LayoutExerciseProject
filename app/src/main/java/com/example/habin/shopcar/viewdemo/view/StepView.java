package com.example.habin.shopcar.viewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.habin.shopcar.R;


/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/7/28
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StepView extends View {


    private int mStepTextColor = Color.BLACK;

    private int mInnerColor = Color.YELLOW;

    private int mOuterColor = Color.BLUE;

    private int mStrokeWidth = 4; //4px

    private int mStepTextSize = 16; //4px

    private Paint mTextPaint;

    private Paint mInnerPaint;

    private Paint mOuterPaint;

    /**
     * 最大步数  可以超过
     */
    private int mStepMax = 10000;
    /**
     * 当前步数
     */
    private int mCurrentStep = 5000;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        mStepTextColor = array.getColor(R.styleable.StepView_StepTextColor, mStepTextColor);
        mInnerColor = array.getColor(R.styleable.StepView_InnerColor, mInnerColor);
        mOuterColor = array.getColor(R.styleable.StepView_OuterColor, mOuterColor);
        mStrokeWidth = array.getDimensionPixelOffset(R.styleable.StepView_StrokeWidth, spToPx(mStrokeWidth));
        mStepTextSize = array.getDimensionPixelOffset(R.styleable.StepView_StepTextSize, spToPx(mStepTextSize));

        /** 回收*/
        array.recycle();

        mTextPaint = new Paint();
        //抗锯齿
        mTextPaint.setAntiAlias(true);
        //设置字体大小
        mTextPaint.setTextSize(mStepTextSize);
        //设置画笔颜色
        mTextPaint.setColor(mStepTextColor);

        mInnerPaint = setPaint(mInnerPaint, mInnerColor);
        mOuterPaint = setPaint(mOuterPaint, mOuterColor);


    }

    private int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp
                , getResources().getDisplayMetrics());
    }

    private Paint setPaint(Paint paint, int color) {
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //设置画笔宽度
        paint.setStrokeWidth(mStrokeWidth);
        //设置画笔颜色
        paint.setColor(color);
        //设置线帽
        paint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔样式
        // Paint.Style.FILL 填充内容
        // Paint.Style.STROKE 描边
        // Paint.Style.FILL_AND_STROKE
        paint.setStyle(Paint.Style.STROKE);

        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        /** 当布局中使用 wrap_content 设置为300dp*/
        if (widthMode == MeasureSpec.AT_MOST) {
            /** 300dp*/
            widthSize = 600;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = 600;
        }

        int size = Math.min(widthSize, heightSize);
        setMeasuredDimension(size, size);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /** 中心点*/
        int center = getWidth() / 2;
        /** 画笔的宽度*/
        int radius = center - mStrokeWidth / 2;
        RectF arcRect = new RectF(center - radius, center - radius
                , center + radius, center + radius);
        /** 1 画外圆*/
        canvas.drawArc(arcRect, 135, 270, false, mOuterPaint);
        if (mStepMax == 0) return;
        /** 内圈比例*/
        float sweepAngle = (float) (Math.min(mCurrentStep, mStepMax)) / mStepMax;
        /** 2 画内圆*/
        canvas.drawArc(arcRect, 135, sweepAngle * 270, false, mInnerPaint);
        /** 3 步数*/
        /** 转文字*/
        String stepText = mCurrentStep + "";
        /** 设置文字位置属性*/
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), bounds);
        /** 文字开头位置*/
        int dx = center - bounds.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        /** 基线位置*/
        int baseLine = getHeight() / 2 + dy;
        /** 画文字*/
        canvas.drawText(stepText, dx, baseLine, mTextPaint);
    }


    public void setStepMa(int stepMax) {
        this.mStepMax = stepMax;
    }

    public void setCurrentStep(int currentStep) {
        this.mCurrentStep = currentStep;
        /** 重绘*/
        invalidate();
    }
}
