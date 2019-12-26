package com.example.habin.shopcar.cutleryRecycling;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.adapter.VpAdapter;
import com.example.habin.shopcar.cutleryRecycling.fragment.BeRecyledFragment;
import com.example.habin.shopcar.cutleryRecycling.fragment.MyRecyleFragment;
import com.example.habin.shopcar.cutleryRecycling.fragment.ToBeRecyledFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CutleryRecyclingActivity extends AppCompatActivity {


    private ImageView mIvBack;
    private ImageView mIvSearch;
    private TabLayout mTlTabs;
    private ViewPager mVpContent;
    private VpAdapter mVpAdapter;
    private List<Fragment> mFragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutlery_recycling);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {

        mTlTabs = findViewById(R.id.tl_tabs);
        mVpContent = findViewById(R.id.vp_content);
        mIvBack = findViewById(R.id.iv_back);
        mIvSearch = findViewById(R.id.iv_search);
        String[] titles = getResources().getStringArray(R.array.tableware_recycle_status);
        for (String tab : titles) {
            mTlTabs.addTab(mTlTabs.newTab().setText(tab));
        }
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ToBeRecyledFragment.newInstance());//待回收8
        mFragmentList.add(BeRecyledFragment.newInstance());//已回收7
        mFragmentList.add(MyRecyleFragment.newInstance());//我回收的9

        mVpAdapter = new VpAdapter(getSupportFragmentManager(), mFragmentList, titles);
        mVpContent.setAdapter(mVpAdapter);
        //预加载
        mVpContent.setOffscreenPageLimit(titles.length);
        mTlTabs.setupWithViewPager(mVpContent);
    }

    private void initEvent() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CutleryRecyclingActivity.this, SearchRecylingActivity.class));
            }
        });
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
