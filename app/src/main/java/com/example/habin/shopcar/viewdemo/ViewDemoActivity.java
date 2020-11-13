package com.example.habin.shopcar.viewdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.habin.shopcar.BaseActivity;
import com.example.habin.shopcar.R;
import com.example.habin.shopcar.viewdemo.view.ColorTrackTextView;
import com.example.habin.shopcar.viewdemo.view.StepView;
import com.example.habin.shopcar.viewdemo.view.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewDemoActivity extends BaseActivity {


    @BindView(R.id.tv_context)
    TextView tvContext;
    @BindView(R.id.sv_step)
    StepView mSvStep;
    @BindView(R.id.ctcv_text)
    ColorTrackTextView mCtcvText;

    @Override
    protected int getLayout() {
        return R.layout.activity_view_demo;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        /** 属性动画*/
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 9000);
        valueAnimator.setDuration(1000);
        /** 插值器 速度设置器*/
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            mSvStep.setCurrentStep((int) animatedValue);
        });
        valueAnimator.start();
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_left_to_right:
                mCtcvText.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,1);
                valueAnimator.setDuration(2000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentProgress = (float) animation.getAnimatedValue();
                        mCtcvText.setCurrentProgress(currentProgress);
                    }
                });
                valueAnimator.start();
                break;
            case R.id.bt_right_to_left:
                mCtcvText.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                ValueAnimator valueAnimator2 = ObjectAnimator.ofFloat(0,1);
                valueAnimator2.setDuration(2000);
                valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentProgress = (float) animation.getAnimatedValue();
                        mCtcvText.setCurrentProgress(currentProgress);
                    }
                });
                valueAnimator2.start();
                break;
        }
    }


}