package com.example.habin.shopcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.habin.shopcar.btnNav.BtnNavActivity;
import com.example.habin.shopcar.cutleryRecycling.CutleryRecyclingActivity;
import com.example.habin.shopcar.salaryTest.SalaryActivity;
import com.example.habin.shopcar.shopCar.MainActivity;
import com.example.habin.shopcar.signature.SignatureActivity;
import com.example.habin.shopcar.utils.WaterMark;
import com.example.habin.shopcar.viewdemo.ViewDemoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.tv_shop_car)
    Button tvShopCar;
    @BindView(R.id.tv_cutlery)
    Button tvCutlery;
    @BindView(R.id.tv_salary)
    Button tvSalary;
    @BindView(R.id.tv_test1)
    Button tvTest1;
    @BindView(R.id.tv_test2)
    Button tvTest2;
    @BindView(R.id.tv_test3)
    Button tvTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        WaterMark.getInstance().show(this, "曹操曹操曹操");
    }

    @OnClick({R.id.tv_shop_car, R.id.tv_cutlery, R.id.tv_salary, R.id.tv_test1, R.id.tv_test2, R.id.tv_test3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shop_car:
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                break;
            case R.id.tv_cutlery:
                startActivity(new Intent(StartActivity.this, CutleryRecyclingActivity.class));
                break;
            case R.id.tv_salary:
                startActivity(new Intent(StartActivity.this, SalaryActivity.class));
                break;
            case R.id.tv_test1:
                startActivity(new Intent(StartActivity.this, BtnNavActivity.class));
                break;
            case R.id.tv_test2:
                startActivity(new Intent(StartActivity.this, SignatureActivity.class));
                break;
            case R.id.tv_test3:
                startActivity(new Intent(StartActivity.this, ViewDemoActivity.class));
                break;
        }
    }
}
