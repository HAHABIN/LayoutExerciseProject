package com.example.habin.shopcar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habin.shopcar.gson.ShopCar;
import com.example.habin.shopcar.gson.items;
import com.example.habin.shopcar.utils.HttpUtils;
import com.example.habin.shopcar.utils.Uitlity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bg_title)
    TextView bgTitle;
    @BindView(R.id.shop_car_num)
    TextView shopCarNum;
    @BindView(R.id.elv_shopping_car)
    ExpandableListView lv;
    @BindView(R.id.all_chekbox)
    CheckBox allChekbox;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_go_to_pay)
    TextView tvGoToPay;

    private ShopCar shopCar;
    private List<items> itemsList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ShopCarWithOkHttp();

    }

    private void Event() {
        myAdapter.setOnCartListChangeListener(new MyAdapter.onCartListChangeListener() {
            @Override
            public void onSellerCheckedChange(int groupPosition) {
                //商家被点击
                boolean currentSellerAllGoodSelected = myAdapter.isCurrentSellerAllGoodSelected(groupPosition);
                myAdapter.changeCurrentSellerAllGoodStatus(groupPosition, !currentSellerAllGoodSelected);
                myAdapter.notifyDataSetChanged();
                //B.刷新底部数据
                refreshSelectedAndTotalPriceAndTotalNumber();
            }

            @Override
            public void onProductCheckedChange(int groupPosition, int childPosition) {
                //点击商品得checkbox
                myAdapter.changeCurrentGoodStatus(groupPosition, childPosition);
                myAdapter.notifyDataSetChanged();
                //B.刷新底部数据
                refreshSelectedAndTotalPriceAndTotalNumber();
            }

            @Override
            public void onProducNumberChange(int groupPosition, int childPosition, String number) {
                //当加减被点击
                myAdapter.changeCurrentGoodNumber(groupPosition, childPosition, Integer.parseInt(number));
                myAdapter.notifyDataSetChanged();
                //B.刷新底部数据
                refreshSelectedAndTotalPriceAndTotalNumber();
            }
        });
    }

    private void initView() {
        myAdapter = new MyAdapter(this, itemsList);
        //去掉ExpandableListView 默认的箭头
        lv.setGroupIndicator(null);

        lv.setAdapter(myAdapter);
        //遍历所有group,将所有项设置展开
        int groupCount = lv.getCount();
        for (int i = 0; i < groupCount; i++) {
            lv.expandGroup(i);
        }


    }

    //B.刷新checkbox状态和总价和总数量
    private void refreshSelectedAndTotalPriceAndTotalNumber() {
        //去判断是否所有得商品都被选中
        boolean allProductsSelected = myAdapter.isAllGoodsSelected();
        //设置给全选checkBox
        allChekbox.setChecked(allProductsSelected);

        //计算总价
        float totalPrice = myAdapter.calculateTotalPrice();
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String totalPrice2 = fnum.format(totalPrice);
        tvTotalPrice.setText("总价 " + totalPrice2);

        //计算总数量
        int totalNumber = myAdapter.calculateTotalNumber();
        tvGoToPay.setText("去结算(" + totalNumber + ")");
    }

    private void ShopCarWithOkHttp() {
        HttpUtils.ShopCarWithOkHttp(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == 200) {
                    final String json = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MainAc", "onResponse: " + json);
                            shopCar = Uitlity.handleShowResponse(json);
                            Log.d("MainAc", "onResponse: " + shopCar.getItemsList().get(1).toString());
                            itemsList = shopCar.getItemsList();
                            initView();
                            Event();
                        }
                    });
                }


            }
        });
    }

    @OnClick({R.id.all_chekbox, R.id.tv_go_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_chekbox:
                //底部全选按钮
                //时候所有得商品都被选中
                boolean allGoodsSelected = myAdapter.isAllGoodsSelected();
                myAdapter.changeAllGoodStatus(!allGoodsSelected);
                myAdapter.notifyDataSetChanged();
                //刷新底部数据
                refreshSelectedAndTotalPriceAndTotalNumber();
                break;
            case R.id.tv_go_to_pay:
                float totalPrice = myAdapter.calculateTotalPrice();
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String totalPrice2 = fnum.format(totalPrice);
                Toast.makeText(this, "请支付为："+totalPrice2+"元", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
