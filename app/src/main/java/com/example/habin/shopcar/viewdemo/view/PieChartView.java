/**
 * 文 件 名:  PieChartView
 * 版    权:  QuanTeng Tech. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  HABIN
 * 修改时间:  2020/11/16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.example.habin.shopcar.viewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <饼状图> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/11/16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PieChartView extends View {

    private Paint paint;


    public PieChartView(Context context) {
        this(context,null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /** 中心点*/
        int center = getWidth() / 2;
        /** 画笔的宽度*/
        int radius = center;
        RectF arcRect = new RectF(0, 0
                , getWidth(), getHeight());
        paint.setColor(Color.RED);
        canvas.save();
        canvas.translate(10,10);
        canvas.drawArc(arcRect,0,60,true,paint);
        canvas.restore();
        paint.setColor(Color.BLUE);
        canvas.save();
        canvas.drawArc(arcRect,60,90,true,paint);
        canvas.restore();
        paint.setColor(Color.GREEN);
        canvas.save();
        canvas.drawArc(arcRect,90,150,true,paint);
        canvas.restore();
    }
}
