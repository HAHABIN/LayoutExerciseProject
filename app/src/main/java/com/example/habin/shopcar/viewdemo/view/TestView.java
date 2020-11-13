/**
 * 文 件 名:  TestView
 * 版    权:  QuanTeng Tech. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  HABIN
 * 修改时间:  2020/11/13
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.example.habin.shopcar.viewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.habin.shopcar.utils.CommonUtils;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/11/13
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TestView extends View {


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path path = new Path();

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        path.addRect(getWidth() / 2 - 150, getHeight() / 2 - 300
                , getWidth() / 2 + 150, getHeight() / 2, Path.Direction.CW);
        path.addCircle(getWidth()/2,getHeight()/2,150,Path.Direction.CW);
        path.addCircle(getWidth()/2,getHeight()/2,400,Path.Direction.CW);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(path,paint);
    }
}
