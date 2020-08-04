package com.example.habin.shopcar.viewdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.habin.shopcar.BaseActivity;
import com.example.habin.shopcar.R;
import com.example.habin.shopcar.viewdemo.view.StepView;
import com.example.habin.shopcar.viewdemo.view.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewDemoActivity extends BaseActivity {


    @BindView(R.id.tv_context)
    TextView tvContext;
    @BindView(R.id.sv_step)
    StepView mSvStep;

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
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mSvStep.setCurrentStep((int) animatedValue);
            }
        });
        valueAnimator.start();
    }
}