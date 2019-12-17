package com.example.habin.shopcar.cutleryRecycling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.shopcar.R;

public class SearchRecylingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtSearch;
    private ImageView mIvClean;
    private LinearLayout mLlNoOrder;

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
//        mMainLayout.findViewById(R.id.navbar_base).setVisibility(View.GONE);
        mEtSearch = findViewById(R.id.et_search);
        mIvClean = findViewById(R.id.iv_clean);
        mLlNoOrder = findViewById(R.id.ll_no_order);

    }
    private void initEvent() {
        mEtSearch.setOnClickListener(this);
        mIvClean.setOnClickListener(this);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
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
                    mLlNoOrder.setVisibility(View.VISIBLE);
                }
                return false;

            }
        });
        //取消退出
        findViewById(R.id.tv_out).setOnClickListener(this);
    }


    public void initload(){
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
}
