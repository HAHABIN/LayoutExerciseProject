package com.example.habin.shopcar.viewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.habin.shopcar.utils.CommonUtils;

/**
 * <字母侧栏索引> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/11/26
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LetterSideBarView extends View {


    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mCurrent = 0;

    public LetterSideBarView(Context context) {
        this(context, null);
    }

    public LetterSideBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setTextSize(CommonUtils.spToPx(12)); //设置像素
        mPaint.setColor(Color.BLUE);
    }

    /**
     * 指定宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getPaddingLeft() + getPaddingRight() + (int) mPaint.measureText("A");
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / 27;

        char str = 'A';
        for (int i = 0; i < 27; i++) {
            String s = i == 26 ? "#" : String.valueOf(str++);
            /** 知道每个字母的中心位置 1 字母高度的一半  2 字符高度一半 + 前面字符的高度 */
            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            /** 中心点到基准线的距离 bottom（正数） 和 top（负数） 是相对BaseLine偏移量  */
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            /** 字母基线*/
            int baseLine = dy + letterCenterY;
            /** 处理字母居中  获取View宽度一半 再减去字母宽度一半 最终x为字母x轴起点*/
            int x = getWidth() / 2 - (int) mPaint.measureText(s) / 2;
            //高亮
            if (i == mCurrent) {
                mPaint.setColor(Color.RED);
            } else {
                mPaint.setColor(Color.BLUE);
            }
            canvas.drawText(s, x, baseLine, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算当前触摸字母位置
                float currentMoveY = event.getY();
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / 27;
                int count = (int) (currentMoveY) / itemHeight;
                if (count < 0) {
                    count = 0;
                }
                if (count > 27) {
                    count = 26;
                }
                if (mShowTouchText!=null) {
                    char str = (char) ('A' + count);
                    mShowTouchText.showTouchText(str,true);
                }
                if (count == mCurrent) {
                    return true;
                }
                mCurrent = count;

                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP: //抬起监听
                if (mShowTouchText!=null) {
                    mShowTouchText.showTouchText('A',false);
                }
                break;
        }

        return true;
    }

    private showTouchText mShowTouchText;

    public void setShowTouchText(showTouchText mShowTouchText) {
        this.mShowTouchText = mShowTouchText;
    }

    public interface showTouchText {
        void showTouchText(char str,boolean isShow);
    }
}
