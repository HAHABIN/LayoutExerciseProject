package com.example.habin.shopcar.salaryTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    @BindView(R.id.tv_dep_name)
    TextView mvDepName;
    @BindView(R.id.tv_range)
    TextView mTvRange;

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
        String[] titles = getResources().getStringArray(R.array.salary_status);
        //配置横向滚动
        rvTopnav.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //顶部导航栏适配器
        mTopAdapter = new TopNavRecyclerAdapter(getApplicationContext(), titles, SalaryActivity.this);
        //导航栏设置适配器
        rvTopnav.setAdapter(mTopAdapter);

        //ViewPager下的Fragment
        mFragmentList = new ArrayList<>();
        mFragmentList.add(SalaryListFragment.newInstance(mDataList.get(0)));
        mFragmentList.add(SalaryListFragment.newInstance(mDataList.get(1)));
        mFragmentList.add(SalaryListFragment.newInstance(mDataList.get(2)));
        mFragmentList.add(NoteFragment.newInstance(mDataList.get(3)));
        //ViewPager适配器 传入Fragment列表
        mVpAdapter = new VpAdapter(getSupportFragmentManager(), mFragmentList, titles);
        //ViewPager配置适配器
        mVpContent.setAdapter(mVpAdapter);
        //预加载
        mVpContent.setOffscreenPageLimit(titles.length);
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
                    }
                    recordDetailsEntity.RecordData infoList;
                    for (int i = 0;i<mResult.getData().size();i++){
                        infoList =  mResult.getData().get(i);
                        int type = infoList.getId();
                        switch (type){
                            case 1:
                                setInfo(infoList);
                                break;
                            case 2:
                                mDataList.add(0,infoList);
                                break;
                            case 3:
                                mDataList.add(1,infoList);
                                break;
                            case 4:
                                mDataList.add(2,infoList);
                                break;
                            case 5:
                                mDataList.add(3,infoList);
                                break;
                        }
                    }

                    initView();

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

    //基本信息填写
    public void setInfo(recordDetailsEntity.RecordData infoList) {
        List<recordDetailsEntity.RecordData.salaryRecordInfoEditVO> data = infoList.getSalaryRecordInfoEditVOList();
        mTvTeacherId.setText(data.get(0).getFieldValue());
        mTvInfo.setText(data.get(2).getFieldValue());
        mvDepName.setText(data.get(1).getFieldValue());
        mTvRange.setText(data.get(3).getFieldValue());
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
