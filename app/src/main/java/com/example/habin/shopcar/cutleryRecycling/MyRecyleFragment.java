package com.example.habin.shopcar.cutleryRecycling;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.R;


public class MyRecyleFragment extends Fragment {


    public static MyRecyleFragment newInstance() {
        return new MyRecyleFragment();
    }

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
    }


}
