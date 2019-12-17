package com.example.habin.shopcar.cutleryRecycling;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToBeRecyledFragment extends Fragment {


    public static ToBeRecyledFragment newInstance() {
        return new ToBeRecyledFragment();
    }

    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_to_be_recyled, container, false);
        initView();
        return mView;
    }

    private void initView() {

    }


}
