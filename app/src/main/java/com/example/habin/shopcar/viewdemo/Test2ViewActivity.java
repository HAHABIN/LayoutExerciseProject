package com.example.habin.shopcar.viewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.viewdemo.view.LetterSideBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Test2ViewActivity extends AppCompatActivity implements LetterSideBarView.showTouchText {

    @BindView(R.id.lsb_test)
    LetterSideBarView lsbTest;
    @BindView(R.id.tv_letter)
    TextView tvLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_view);
        ButterKnife.bind(this);
        lsbTest.setShowTouchText(this);
    }

    @Override
    public void showTouchText(char str, boolean isShow) {
        if (isShow) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText(String.valueOf(str));
        } else {
            tvLetter.setVisibility(View.GONE);
        }
    }
}