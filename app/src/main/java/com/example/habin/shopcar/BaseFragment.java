package com.example.habin.shopcar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by habin
 * on 2019/12/27
 * fragment基类
 */
public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    Unbinder unbinder;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null) {
                parent.removeView(mRoot);
            }
            return mRoot;
        }
        mRoot = LayoutInflater.from(getActivity()).inflate(getLayoutRes(),container,false);
        unbinder = ButterKnife.bind(this, mRoot);
        initView(mRoot);
        initListener();
        initData();
        return mRoot;
    }

    protected abstract int getLayoutRes();
    protected abstract void initView(View view);
    protected abstract void initListener();
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
