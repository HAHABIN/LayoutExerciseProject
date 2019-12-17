package com.example.habin.shopcar.cutleryRecycling;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.adapter.BeRecyledAdapter;


public class MyRecyleFragment extends Fragment {


    public static MyRecyleFragment newInstance() {
        return new MyRecyleFragment();
    }

    private SwipeView mSwipeView;
    private BeRecyledAdapter mAdapter;
    private int mPageNo = 1;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_recyle, container, false);
        initView();
        return mView;
        // Inflate the layout for this fragment
    }

    private void initView() {

        mSwipeView = mView.findViewById(R.id.swipeView);
        mAdapter = new BeRecyledAdapter(getContext(),"9");
        mSwipeView.setAdapter(mAdapter);
        mSwipeView.setReLoadAble(true);
        mSwipeView.setOnRefreshListener(new SwipeView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
            }
        });
        mSwipeView.setOnReLoadListener(new SwipeView.OnReLoadListener() {
            @Override
            public void onLoad() {
                mPageNo++;
            }
        });
    }


}
