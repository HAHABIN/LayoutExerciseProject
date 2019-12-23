package com.example.habin.shopcar.cutleryRecycling.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.view.SwipeView;
import com.example.habin.shopcar.cutleryRecycling.adapter.BeRecyledAdapter;
import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;
import com.example.habin.shopcar.cutleryRecycling.service.HttpEngine;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeRecyledFragment extends Fragment implements BeRecyledAdapter.IitemCallback {

    private View mView;
    private BeRecyledAdapter mAdapter;
    private SwipeView mSwipeView;
    private int mPageNo = 1;
    private int mPageSize = 10;
    private List<RecycleOrderListEntity.ItemBean> mDataList;

    public static BeRecyledFragment newInstance() {
        return new BeRecyledFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_be_recyled, container, false);

        initView();
        return mView;
    }


    private void initView() {

        mSwipeView = mView.findViewById(R.id.swipeView);
        mAdapter = new BeRecyledAdapter(getContext(),this);
        mSwipeView.setAdapter(mAdapter);
        mSwipeView.setReLoadAble(true);
        mSwipeView.setOnRefreshListener(new SwipeView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                load();
            }
        });
        mSwipeView.setOnReLoadListener(new SwipeView.OnReLoadListener() {
            @Override
            public void onLoad() {
                mPageNo++;
                load();
            }
        });
        mSwipeView.startRefresh();
    }

    private void load() {

        HttpEngine.getDataList(mPageNo, mPageSize, "7", null, null, null, new Observer<RecycleOrderListEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(RecycleOrderListEntity mResult) {
                mSwipeView.stopFreshing();
                if (mResult != null) {
                    List<RecycleOrderListEntity.ItemBean> tempList = mResult.getItem();
                    if (mDataList==null){
                        mDataList= new ArrayList<>();
                    }
                    if (mPageNo == 1) {
                        mDataList.clear();
                        //判断对象是否为空 和值是否为空
                        if (tempList != null && !tempList.isEmpty()) {
                            mDataList.addAll(tempList);
                            mSwipeView.setReLoadAble(tempList.size() == mPageSize);
                        }
                    } else {
                        mDataList.addAll(tempList);
                        if (tempList.size() <= mPageSize) {
                            mDataList.addAll(tempList);
                            mSwipeView.setReLoadAble(tempList.size() == mPageSize);
                        }

                    }
                    mAdapter.setDataList(mDataList);
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void callPhone(String phoneNum) {
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
//        startActivity(intent);

    }

    @Override
    public void confirmRecycle(long orderId) {

    }

}
