package com.example.habin.shopcar.salaryTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.adapter.VpAdapter;
import com.example.habin.shopcar.salaryTest.Entity.recordDetailsEntity;
import com.example.habin.shopcar.salaryTest.adapter.TopNavRecyclerAdapter;
import com.example.habin.shopcar.salaryTest.fragment.NoteFragment;
import com.example.habin.shopcar.salaryTest.fragment.SalaryListFragment;
import com.example.habin.shopcar.salaryTest.http.HttpEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SalaryActivity extends AppCompatActivity implements TopNavRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.tv_info)
    TextView mTvInfo;
    @BindView(R.id.rl_header)
    RelativeLayout mRlHeader;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.rv_topnav)
    RecyclerView rvTopnav;
    @BindView(R.id.tv_teacher_id)
    TextView mTvTeacherId;


    private List<String> mTitle;
    private VpAdapter mVpAdapter;
    private TopNavRecyclerAdapter mTopAdapter;
    private List<Fragment> mFragmentList;
    private List<recordDetailsEntity.RecordData> mDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        ButterKnife.bind(this);
        initload();

    }


    private void initView() {
        //RecyclerView顶部导航栏名称数组
//        String[] titles = getResources().getStringArray(R.array.salary_status);
        //配置横向滚动
        rvTopnav.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //顶部导航栏适配器
        mTopAdapter = new TopNavRecyclerAdapter(getApplicationContext(), mTitle, SalaryActivity.this);
        //导航栏设置适配器
        rvTopnav.setAdapter(mTopAdapter);

        //ViewPager下的Fragment
        mFragmentList = new ArrayList<>();
        for (int i = 0;i<mTitle.size();i++){
            if (mTitle.get(i).equals("备注")){
                mFragmentList.add(NoteFragment.newInstance(mDataList.get(i)));
            }else {
                mFragmentList.add(SalaryListFragment.newInstance(mDataList.get(i)));
            }
        }

        //ViewPager适配器 传入Fragment列表
        mVpAdapter = new VpAdapter(getSupportFragmentManager(), mFragmentList, mTitle);
        //ViewPager配置适配器
        mVpContent.setAdapter(mVpAdapter);
        //预加载
        mVpContent.setOffscreenPageLimit(mTitle.size());
        //Viewpager滑动监听
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int postion, float v, int i1) {

            }

            @Override
            public void onPageSelected(int postion) {
                //ViewPager滑动传值给 顶部导航栏RecycleView
                mTopAdapter.setStaus(postion);
            }

            @Override
            public void onPageScrollStateChanged(int postion) {

            }
        });
    }

    private void initload() {
        HttpEngine.getrecordDetails("5", "2019007", new Observer<recordDetailsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(recordDetailsEntity mResult) {
                if (mResult != null) {
                    if (mDataList == null) {
                        mDataList = new ArrayList<>();
                        mTitle = new ArrayList<>();
                    }
                    mDataList = mResult.getData();
                    for (int i = 0; i < mDataList.size(); i++) {
                        //获得导航栏名
                        String title = mDataList.get(i).getFieldTypeName();
                        mTitle.add(title);
                    }
                    initView();

                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SalaryActivity.this, "获取信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    //基本信息填写
    public void setInfo(recordDetailsEntity.RecordData infoList) {
        List<recordDetailsEntity.RecordData.salaryRecordInfoEditVO> data = infoList.getSalaryRecordInfoEditVOList();
        mTvTeacherId.setText(data.get(0).getFieldValue());
        mTvInfo.setText(data.get(2).getFieldValue());

    }

    @Override
    public void onItemClick(int position) {
        //顶部导航栏点击回调
        mVpContent.setCurrentItem(position);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
