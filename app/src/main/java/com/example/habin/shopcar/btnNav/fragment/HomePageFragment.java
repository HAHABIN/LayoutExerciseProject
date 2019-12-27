package com.example.habin.shopcar.btnNav.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.BaseFragment;
import com.example.habin.shopcar.R;

/**
 * created by habin
 * on 2019/12/27
 * 首页碎片
 */
public class HomePageFragment extends BaseFragment {

    public static HomePageFragment newInstance(){
        return new HomePageFragment();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_page_home;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
