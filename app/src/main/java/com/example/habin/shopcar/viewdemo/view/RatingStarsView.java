package com.example.habin.shopcar.viewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.utils.CommonUtils;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/11/17
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RatingStarsView extends View {

    private static final String TAG = "RatingStarsView.class";

    private Bitmap mStarNormalBitmap;

    private Bitmap mStarSelectedBitmap;

    private int startCount = 3; // 默认3颗

    private float starSize = CommonUtils.dp2px(25);

    private float starMargin = CommonUtils.dp2px(5);

    private int currStarCount = 0;

    public RatingStarsView(Context context) {
        this(context, null);
    }

    public RatingStarsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingStarsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingStarsView);
        /** 如果不设置默认图标 设置为0  如果为0时 说明用户没有设置 抛出异常提示用户
         *         if (starNormalId == 0 ) {
         *             throw new RuntimeException("请设置树形 startNormal");
         *         }
         * */
        int starNormalId = array.getResourceId(R.styleable.RatingStarsView_normalStar, R.mipmap.ic_star_normal);
        mStarNormalBitmap = setImgSize(BitmapFactory.decodeResource(getResources(), starNormalId), starSize);
        int starSelectedId = array.getResourceId(R.styleable.RatingStarsView_selectedStar, R.mipmap.ic_star_selected);
        mStarSelectedBitmap = setImgSize(BitmapFactory.decodeResource(getResources(), starSelectedId), starSize);
        startCount = array.getInt(R.styleable.RatingStarsView_starCount, startCount);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(widthMeasureSpec);
        int currWidth = (int) ((starSize + starMargin) * startCount - starMargin);
        /** 如果当前宽 为精确的值包括match_parent 重新设置星星的宽高*/
        if (widthMode == MeasureSpec.EXACTLY) {
            /** 获取当前*/
            if (currWidth > width) {
                starMargin = CommonUtils.dp2px(2);
                starSize = width / startCount - starMargin;
                mStarNormalBitmap = setImgSize(mStarNormalBitmap, starSize);
                mStarSelectedBitmap = setImgSize(mStarSelectedBitmap, starSize);
            }
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            width = currWidth;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            height = (int) CommonUtils.dp2px(40);
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < startCount; i++) {
            int x = (int) (i * (starSize + starMargin));
            if (currStarCount > i) {
                canvas.drawBitmap(mStarSelectedBitmap, x, 0, null);
            } else {
                canvas.drawBitmap(mStarNormalBitmap, x, 0, null);
            }

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
            case MotionEvent.ACTION_MOVE://移动
//            case MotionEvent.ACTION_UP: //抬起
                float moveX = event.getX(); //getX()：相对控件位置 getRawX()： 相对屏幕位置
                Log.e(TAG, "moveX ->: " + moveX);

                int starCount = (int) (moveX / (starSize + starMargin)) + 1;
                Log.e(TAG, "currStarCount =: " + currStarCount);
                if (starCount < 0) {
                    starCount = 0;
                }

                if (starCount > startCount) {
                    starCount = startCount;
                }
                if (starCount == currStarCount) {
                    return true;
                }
                currStarCount = starCount;

                Log.e(TAG, "invalidate: " + "==");

                invalidate();

        }
        return true;

    }

    public void setCurrStarCount(int currStarCount) {
        this.currStarCount = currStarCount;
    }

    public float getCurrStarCount() {
        return currStarCount;
    }


    /**
     * 修改图片宽高
     *
     * @param bm
     * @param starSize
     * @return
     */
    public Bitmap setImgSize(Bitmap bm, float starSize) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = starSize / width;
        float scaleHeight = starSize / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }
}
