package com.example.habin.shopcar.cutleryRecycling.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.habin.shopcar.BaseFragment;
import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.adapter.BeRecyledAdapter;
import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;
import com.example.habin.shopcar.cutleryRecycling.http.HttpEngine;
import com.example.habin.shopcar.cutleryRecycling.view.AlertDialogView;
import com.example.habin.shopcar.cutleryRecycling.view.SwipeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToBeRecyledFragment extends BaseFragment implements BeRecyledAdapter.IitemCallback {


    private static final String TAG = "ToBeRecyledFragment";
    Unbinder unbinder;

    public static ToBeRecyledFragment newInstance() {

        return new ToBeRecyledFragment();
    }

    public static ToBeRecyledFragment newInstance(String data) {
        ToBeRecyledFragment fragment = new ToBeRecyledFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        fragment.setArguments(bundle);

        return fragment;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + getActivity());
        String url = null;
        if (url != null) {
            Bundle arguments = this.getArguments();
            String name = arguments.getString("data");
            Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_to_be_recyled;
    }


    protected void initView(View view) {


        mTvFirst = view.findViewById(R.id.tv_first_delivery);
        mIvFirst = view.findViewById(R.id.iv_first_delivery);
        mTvArea = view.findViewById(R.id.tv_recycling_area);
        mIvArea = view.findViewById(R.id.iv_recycling_area);

        mSwipeView = view.findViewById(R.id.swipeView);
        mAdapter = new BeRecyledAdapter(getContext(), this);
        mSwipeView.setAdapter(mAdapter);
        mSwipeView.setReLoadAble(true);
        mSwipeView.setOnRefreshListener(new SwipeView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                initData();
            }
        });
        mSwipeView.setOnReLoadListener(new SwipeView.OnReLoadListener() {
            @Override
            public void onLoad() {
                mPageNo++;
                initData();
            }
        });

        mTimeList = new ArrayList<>();
        mTimeList.add("时间排序");
        mTimeList.add("地址排序");


        //地区选择列表
        BuildingListLoad();
        mSwipeView.startRefresh();
    }

    @Override
    protected void initListener() {

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
    protected void initData() {


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
        AlertDialogView dialog = new AlertDialogView(getActivity());
        dialog.setTitle("回收");
        dialog.setMessage("是否确认回收");
        dialog.setConfimStr("确定");
        dialog.setListener(new AlertDialogView.onClickListener() {
            @Override
            public void cancelClick(AlertDialogView dialog) {
                dialog.dismiss();
            }

            @Override
            public void confirmClick(AlertDialogView dialog) {
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

                if (dList != null && !dList.isEmpty()) {
                    String strBankName = dList.get(options1);
                    if (Checked == 1) {
                        mTvFirst.setText(strBankName);//将选中的数据返回设置在TextView 上。
                        switch (strBankName) {
                            case "时间排序":
                                mOrdersType = "time";
                                break;
                            case "地址排序":
                                mOrdersType = "building";
                                break;
                        }
                    } else {
                        if (!strBankName.equals("全部")) {
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



    @OnClick({R.id.choose_first, R.id.choose_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_first:
                mChecked = 1;
                ChooesFirstOrArea();
                ShowBankName(mTimeList, mChecked);
                break;
            case R.id.choose_area:
                mChecked = 2;
                if (mBuildList != null) {
                    ShowBankName(mBuildList, mChecked);
                    ChooesFirstOrArea();
                }

                break;
        }
    }
}
