package com.xtown.gky.cutleryRecycling.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xtown.gky.BaseFragment;

import java.util.List;

public class VpAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragmentList;
    private String[] mTitleList;

    public VpAdapter(FragmentManager fm) {
        super(fm);
    }

    public VpAdapter(FragmentManager fm, List<BaseFragment> mFragmentList, String[] mTitleList) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList[position];
    }
}
