package com.example.habin.shopcar.salaryTest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.salaryTest.Entity.recordDetailsEntity;
import com.example.habin.shopcar.salaryTest.adapter.SalaryListAdapter;
import com.example.habin.shopcar.salaryTest.Entity.itemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SalaryListFragment extends Fragment {

    @BindView(R.id.rv_salaryList)
    RecyclerView mRvSalaryList;
    Unbinder unbinder;


    public static SalaryListFragment newInstance(recordDetailsEntity.RecordData data) {
        SalaryListFragment fragment = new SalaryListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data);
        fragment.setArguments(bundle);
        return fragment;
    }


    private View mView;
    private SalaryListAdapter mAdapter;
    private List<itemEntity> mItems;
    private recordDetailsEntity.RecordData mData;


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
        mView = inflater.inflate(R.layout.fragment_salarylist, container, false);
        unbinder = ButterKnife.bind(this, mView);
        Bundle bundle = this.getArguments();
        mData = (recordDetailsEntity.RecordData) bundle.getSerializable("data");
        initView();

        return mView;
    }

    private void initView() {
        //模拟数据
//        setdata();
        mRvSalaryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SalaryListAdapter(getContext(), mData);
        mRvSalaryList.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    public void setdata() {
//        mItems = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            itemEntity item = new itemEntity();
//            item.setName("工资详细" + i);
//            item.setNum("1天" + i);
//            mItems.add(item);
//        }
//    }
}
