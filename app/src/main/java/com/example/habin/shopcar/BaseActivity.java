package com.example.habin.shopcar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.example.habin.shopcar.utils.ActivityManagerUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    protected static String TAG;

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ActivityManagerUtil.mActivities.add(this);
        TAG = this.getClass().getSimpleName();
        mUnBinder = ButterKnife.bind(this);
        mContext = this;

        initData(savedInstanceState);
        initWidget();
        initEvent();
        initClick();

    }
    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {
    }

    /**
     * 初始化零件
     */
    protected void initWidget() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    /**
     * 初始化点击事件
     */
    protected void initClick() {
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        ActivityManagerUtil.mActivities.remove(this);
    }

    @LayoutRes
    protected abstract int getLayout();






}
