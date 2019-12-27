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
 * 消息碎片
 */
public class MessagePageFragment  extends BaseFragment {

    public static MessagePageFragment newInstance(){
        return new MessagePageFragment();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_page_message;
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
