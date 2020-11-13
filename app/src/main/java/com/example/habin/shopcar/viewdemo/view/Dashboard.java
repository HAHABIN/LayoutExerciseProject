/**
 * 文 件 名:  Dashboard
 * 版    权:  QuanTeng Tech. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  HABIN
 * 修改时间:  2020/11/13
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.example.habin.shopcar.viewdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.util.Util;
import com.example.habin.shopcar.utils.CommonUtils;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/11/13
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Dashboard extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path dash = new Path();
    private static final float RADIUS = CommonUtils.dp2px(150);
    private static final float ANGLE = CommonUtils.dp2px(120);
    private PathDashPathEffect pathDashPathEffect;

    public Dashboard(Context context) {
        this(context,null);
    }

    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Dashboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(CommonUtils.dp2px(2));


    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float bordWidth = CommonUtils.dp2px(2);

        RectF arcRect = new RectF(0+bordWidth
                ,0+bordWidth,getWidth()-bordWidth,getHeight()-bordWidth);

        canvas.drawArc(arcRect,135,270,false,paint);
        Path arr = new Path();
        arr.addArc(arcRect,135,270);
        PathMeasure pathMeasure = new PathMeasure(arr,false);

        /** 仪表盘点 长方形点*/
        dash.addRect(0,0,bordWidth, CommonUtils.dp2px(10),Path.Direction.CW);
        /** 第一个参数 方块，第二个参数 每个方块间距，第三参数 起始点，第四个 风格 ROTATE 围绕中心旋转*/
        pathDashPathEffect = new PathDashPathEffect(dash
                , (pathMeasure.getLength()-bordWidth)/20,
                0,PathDashPathEffect.Style.ROTATE);
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawPath(arr,paint);
        paint.setPathEffect(null);
    }
}
