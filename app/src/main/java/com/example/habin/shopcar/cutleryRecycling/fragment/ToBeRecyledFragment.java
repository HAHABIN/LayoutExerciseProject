package com.example.habin.shopcar.cutleryRecycling.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.view.SwipeView;
import com.example.habin.shopcar.cutleryRecycling.adapter.BeRecyledAdapter;
import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;
import com.example.habin.shopcar.cutleryRecycling.service.HttpEngine;
import com.example.habin.shopcar.cutleryRecycling.view.AlertDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToBeRecyledFragment extends Fragment implements View.OnClickListener, BeRecyledAdapter.IitemCallback {


    public static ToBeRecyledFragment newInstance() {

        return new ToBeRecyledFragment();
    }


    private TextView mTvFirst;
    private ImageView mIvFirst;
    private TextView mTvArea;
    private ImageView mIvArea;
    private SwipeView mSwipeView;
    private BeRecyledAdapter mAdapter;

    /**
     * 配送时间列表集合
     */
    private ArrayList<String> mTimeList;
    /*地区*/
    private ArrayList<String> mBuildList;

    private int mPageNo = 1;
    private int mPageSize = 10;
    private String mOrdersType = "";//选择筛选排序 时间time,地址building
    private String mBuilding = "";
    private int mChecked = 1; //1 默认为最先配送选中  2为回收区域
    private View mView;
    private List<RecycleOrderListEntity.ItemBean> mDataList;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();

        String result = null;
        if (bundle != null) {
            result = bundle.getString("data");
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_to_be_recyled, container, false);


        initView();
        initEvent();

        return mView;
    }


    public void setName(String name) {
        mDataList.get(4).setName(name);
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {


        mTvFirst = mView.findViewById(R.id.tv_first_delivery);
        mIvFirst = mView.findViewById(R.id.iv_first_delivery);
        mTvArea = mView.findViewById(R.id.tv_recycling_area);
        mIvArea = mView.findViewById(R.id.iv_recycling_area);

        mSwipeView = mView.findViewById(R.id.swipeView);
        mAdapter = new BeRecyledAdapter(getContext(), this);
        mSwipeView.setAdapter(mAdapter);
        mSwipeView.setReLoadAble(true);
        mSwipeView.setOnRefreshListener(new SwipeView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                load();
            }
        });
        mSwipeView.setOnReLoadListener(new SwipeView.OnReLoadListener() {
            @Override
            public void onLoad() {
                mPageNo++;
                load();
            }
        });

        mTimeList = new ArrayList<>();
        mTimeList.add("时间排序");
        mTimeList.add("地址排序");


        //地区选择列表
        BuildingListLoad();
        mSwipeView.startRefresh();
    }

    private void initEvent() {
        mView.findViewById(R.id.choose_first).setOnClickListener(this);
        mView.findViewById(R.id.choose_area).setOnClickListener(this);
    }

    //1 为最先配送选中  2为回收区域
    public void ChooesFirstOrArea() {
        if (mChecked == 1) {
            mTvFirst.setTextColor(Color.parseColor("#F3992C"));
            mIvFirst.setBackgroundResource(R.drawable.area_state_pressed);
            mTvArea.setTextColor(Color.parseColor("#808080"));
            mIvArea.setBackgroundResource(R.drawable.area_state_enabled);
        } else {
            mTvArea.setTextColor(Color.parseColor("#F3992C"));
            mIvArea.setBackgroundResource(R.drawable.area_state_pressed);
            mTvFirst.setTextColor(Color.parseColor("#808080"));
            mIvFirst.setBackgroundResource(R.drawable.area_state_enabled);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_first:
                mChecked = 1;
                ChooesFirstOrArea();
                ShowBankName(mTimeList,mChecked);
                break;
            case R.id.choose_area:
                mChecked = 2;
                if (mBuildList!=null){
                    ShowBankName(mBuildList,mChecked);
                    ChooesFirstOrArea();
                }

                break;
        }
    }

    private void load() {

        HttpEngine.getDataList(mPageNo, mPageSize, "8", null, mBuilding, mOrdersType, new Observer<RecycleOrderListEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(RecycleOrderListEntity mResult) {
                mSwipeView.stopFreshing();

                if (mResult != null) {
                    if (mDataList == null) {
                        mDataList = new ArrayList<>();
                    }
                    List<RecycleOrderListEntity.ItemBean> tempList = mResult.getItem();
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

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void callPhone(String phoneNum) {
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
//        startActivity(intent);
    }

    @Override
    public void confirmRecycle(final long orderId) {
        AlertDialog dialog = new AlertDialog(getActivity());
        dialog.setTitle("回收");
        dialog.setMessage("是否确认回收");
        dialog.setConfimStr("确定");
        dialog.setListener(new AlertDialog.onClickListener() {
            @Override
            public void cancelClick(AlertDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void confirmClick(AlertDialog dialog) {
                RecConfirm(orderId);
            }
        });
        dialog.show();

    }

    //确认回收
    public void RecConfirm(long orderId) {
        HttpEngine.recConfirm(orderId, new Observer<RecycleOrderListEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RecycleOrderListEntity recycleOrderListEntity) {
                if (recycleOrderListEntity.getResult().equals("1")) {
                    Toast.makeText(getContext(), "成功回收", Toast.LENGTH_SHORT).show();
                    mSwipeView.startRefresh();
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

    public void BuildingListLoad() {
        HttpEngine.getBuildingList(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(o.toString());
                    Log.d("BUTTTTT", "onNext: " + jsonObject.toString());
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = jsonObject.getJSONArray("list");

                    if (mBuildList == null) {
                        mBuildList = new ArrayList<>();
                        mBuildList.add("全部");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            mBuildList.add(jsonArray.optString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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


    // 弹出条件选择器
    private void ShowBankName(final ArrayList<String> dList, final int Checked) {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (dList!=null&&!dList.isEmpty()){
                    String strBankName = dList.get(options1);
                    if (Checked==1){
                        mTvFirst.setText(strBankName);//将选中的数据返回设置在TextView 上。
                        switch (strBankName){
                            case "时间排序":
                                mOrdersType = "time";
                                break;
                            case "地址排序":
                                mOrdersType = "building";
                                break;
                        }
                    } else {
                        if (!strBankName.equals("全部")){
                            mBuilding = strBankName;
                        } else {
                            mBuilding = "";
                        }

                        mTvArea.setText(strBankName);
                    }

                    mSwipeView.startRefresh();
                }

            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小
                .setSubmitColor(Color.parseColor("#F3992C"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#F3992C"))//取消按钮文字颜色
                .setOutSideCancelable(false)// default is true
                .build();

        pvOptions.setPicker(dList);//条件选择器

        pvOptions.show();
    }
}
