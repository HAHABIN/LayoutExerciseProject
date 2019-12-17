package com.example.habin.shopcar.cutleryRecycling;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.adapter.BeRecyledAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToBeRecyledFragment extends Fragment {


    public static ToBeRecyledFragment newInstance() {
        return new ToBeRecyledFragment();
    }


    private SwipeView mSwipeView;
    private BeRecyledAdapter mAdapter;
    private int mPageNo = 1;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_to_be_recyled, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mSwipeView = mView.findViewById(R.id.swipeView);
        mAdapter = new BeRecyledAdapter(getContext(),"8");
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
