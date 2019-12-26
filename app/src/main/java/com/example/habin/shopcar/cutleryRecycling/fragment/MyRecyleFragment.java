package com.example.habin.shopcar.cutleryRecycling.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.view.SwipeView;
import com.example.habin.shopcar.cutleryRecycling.adapter.BeRecyledAdapter;
import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;
import com.example.habin.shopcar.cutleryRecycling.http.HttpEngine;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MyRecyleFragment extends Fragment implements BeRecyledAdapter.IitemCallback {


    private TextView mTvDailyNum;
    private TextView mTvMonNum;
    private TextView mTvHisNum;
    private View mView;

    public static MyRecyleFragment newInstance() {
        return new MyRecyleFragment();
    }

    private SwipeView mSwipeView;
    private BeRecyledAdapter mAdapter;
    private int mPageNo = 1;
    private int mPageSize = 10;
    private List<RecycleOrderListEntity.ItemBean> mDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }
        mView = inflater.inflate(R.layout.fragment_my_recyle, container, false);
        initView(mView);
        return mView;
    }


    private void initView(View view) {
        mTvDailyNum = view.findViewById(R.id.tv_daily_order_num);
        mTvMonNum = view.findViewById(R.id.tv_month_order_num);
        mTvHisNum = view.findViewById(R.id.tv_history_order_num);

        mSwipeView = view.findViewById(R.id.swipeView);
        mAdapter = new BeRecyledAdapter(getContext(), this);
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

        HttpEngine.getDataList(mPageNo, mPageSize, "9", null, null, null, new Observer<RecycleOrderListEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(RecycleOrderListEntity Result) {
                mSwipeView.stopFreshing();

                if (Result != null) {
                    if (mDataList == null) {
                        mDataList = new ArrayList<>();
                    }
                    mTvDailyNum.setText(String.valueOf(Result.getStatistics().getDay()));
                    mTvMonNum.setText(String.valueOf(Result.getStatistics().getMonth()));
                    mTvHisNum.setText(String.valueOf(Result.getStatistics().getAll()));
                    List<RecycleOrderListEntity.ItemBean> tempList = Result.getItem();

                    if (mPageNo == 1) {
                        mDataList.clear();
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
