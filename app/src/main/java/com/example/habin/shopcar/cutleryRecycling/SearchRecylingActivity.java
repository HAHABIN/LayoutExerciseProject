package com.example.habin.shopcar.cutleryRecycling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.adapter.BeRecyledAdapter;
import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;
import com.example.habin.shopcar.cutleryRecycling.http.HttpEngine;
import com.example.habin.shopcar.cutleryRecycling.view.SwipeView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchRecylingActivity extends AppCompatActivity implements View.OnClickListener, BeRecyledAdapter.IitemCallback {

    private EditText mEtSearch;
    private ImageView mIvClean;
    private LinearLayout mLlNoOrder;
    private SwipeView mSwipeView;
    private String mKey;
    private int mPageNo = 1;
    private int mPageSize = 10;
    private String mOrdersType = "";//选择筛选排序 时间time,地址building
    private String mBuilding = "";

    private BeRecyledAdapter mAdapter;
    private List<RecycleOrderListEntity.ItemBean> mDataList;

    public static void StartAct(Context context) {
        context.startActivity(new Intent(context, SearchRecylingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recyling);
        initView();
        initEvent();
    }


    private void initView() {
        mSwipeView = findViewById(R.id.swipeView);
        mAdapter = new BeRecyledAdapter(this, this);
        mSwipeView.setAdapter(mAdapter);
        mSwipeView.setReLoadAble(true);
        mSwipeView.setOnRefreshListener(new SwipeView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                initload();
            }
        });
        mSwipeView.setOnReLoadListener(new SwipeView.OnReLoadListener() {
            @Override
            public void onLoad() {
                mPageNo++;
                initload();
            }
        });
        mEtSearch = findViewById(R.id.et_search);
        mIvClean = findViewById(R.id.iv_clean);
        mLlNoOrder = findViewById(R.id.ll_no_order);

    }

    private void initEvent() {
        mEtSearch.setOnClickListener(this);
        mIvClean.setOnClickListener(this);
        //监控输入框
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mIvClean.setVisibility(View.GONE);
                } else {
                    mIvClean.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    String searchKey = mEtSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(searchKey)) {
                        mKey = searchKey;
                        initload();
                    }

                }
                return false;

            }
        });
        //取消退出
        findViewById(R.id.tv_out).setOnClickListener(this);
    }


    public void initload() {
        HttpEngine.getDataList(mPageNo, mPageSize, null, mKey, null, null, new Observer<RecycleOrderListEntity>() {
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
                    List<RecycleOrderListEntity.ItemBean> tempList = Result.getItem();
                    if (tempList.size() == 0 && tempList.isEmpty()) {
                        mDataList.clear();
                        mSwipeView.setVisibility(View.GONE);
                        mLlNoOrder.setVisibility(View.VISIBLE);
                    } else {
                        mSwipeView.setVisibility(View.VISIBLE);
                        mLlNoOrder.setVisibility(View.GONE);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_search:
                break;
            case R.id.iv_clean:
                mEtSearch.setText("");
                break;
            case R.id.tv_out:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void callPhone(String phoneNum) {

    }

    @Override
    public void confirmRecycle(long orderId) {

    }
}
