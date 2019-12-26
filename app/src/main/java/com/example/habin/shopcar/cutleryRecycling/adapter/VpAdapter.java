package com.example.habin.shopcar.cutleryRecycling.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.habin.shopcar.salaryTest.Entity.recordDetailsEntity;

import java.util.List;

public class VpAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] mTitleList;
    private List<recordDetailsEntity.RecordData> mDataList;

    public VpAdapter(FragmentManager fm, List<Fragment> mFragmentList, String[] mTitleList) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mTitleList = mTitleList;
    }
    public void setData(List<recordDetailsEntity.RecordData> dataList){
        if (dataList!=null){
            mDataList = dataList;
            notifyDataSetChanged();
        }

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
