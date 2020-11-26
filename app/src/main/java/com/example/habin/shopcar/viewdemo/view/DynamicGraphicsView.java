package com.example.habin.shopcar.viewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <动态图形> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/11/26
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DynamicGraphicsView extends View {

    private Paint mPaint;

    private Path mPath;

    public DynamicGraphicsView(Context context) {
        this(context,null);
    }

    public DynamicGraphicsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DynamicGraphicsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
