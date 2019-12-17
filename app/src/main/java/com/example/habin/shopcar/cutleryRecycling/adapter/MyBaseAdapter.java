package com.example.habin.shopcar.cutleryRecycling.adapter;

import android.content.Context;


import java.util.ArrayList;
import java.util.List;

/**
 * ListView的通用的适配器
 *
 * @param <T>
 */
public abstract class MyBaseAdapter<T> extends ContentAdapter {

    protected List<T> mList = null;
    protected Context mContext = null;
    protected int mItemHright;
    private int screenWidth;

    public MyBaseAdapter(Context context) {
        mContext = context;
    }

    public Context getContext() {

        return mContext;
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public List<T> getmList() {
        return mList;
    }

    public boolean isNull() {
        return getCount() == 0;
    }

    public void setmList(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setItem(int position, T t) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.set(position, t);
        notifyDataSetChanged();
    }

    public void addList(List<T> l) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.addAll(l);
        notifyDataSetChanged();
    }

    public void addItem(T t) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.add(t);
        notifyDataSetChanged();
    }
    public void addItem(T t,int position) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.add(position,t);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void removeItem(T t) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.remove(t);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemListHeight(float ratio) { // listview convertview item height
        return (int)( mContext.getResources().getDisplayMetrics().widthPixels / ratio);
    }

    public int getItemGridHeight(int columNum) {//Gridview item height
        if (mItemHright == 0) {
//            mItemHright = (int) (((double) (mContext.getResources().getDisplayMetrics().widthPixels - Utils.dipToPixels(mContext, 20))) / (double) columNum
//                    + Utils.dipToPixels(mContext, 10));
        }
        return mItemHright;
    }

}