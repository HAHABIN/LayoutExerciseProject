package com.example.habin.shopcar.cutleryRecycling;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.adapter.VpAdapter;

import java.util.ArrayList;
import java.util.List;

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
        initView();
        initEvent();
    }

    private void initView() {
        //标题隐藏
//        mMainLayout.findViewById(R.id.navbar_base).setVisibility(View.GONE);
        mTlTabs = findViewById(R.id.tl_tabs);
        mVpContent = findViewById(R.id.vp_content);
        mIvBack = findViewById(R.id.iv_back);
        mIvSearch = findViewById(R.id.iv_search);
        String[] titles = getResources().getStringArray(R.array.tableware_recycle_status);
        for (String tab:titles){
            mTlTabs.addTab(mTlTabs.newTab().setText(tab));
        }
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ToBeRecyledFragment.newInstance());
        mFragmentList.add(BeRecyledFragment.newInstance());
        mFragmentList.add(MyRecyleFragment.newInstance());

        mVpAdapter = new VpAdapter(getSupportFragmentManager(),mFragmentList,titles);
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
                SearchRecylingActivity.StartAct(CutleryRecyclingActivity.this);
            }
        });
    }


}
