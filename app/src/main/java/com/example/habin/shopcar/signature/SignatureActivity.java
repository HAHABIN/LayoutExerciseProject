package com.example.habin.shopcar.signature;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.habin.shopcar.R;

import java.io.File;
import java.io.IOException;

public class SignatureActivity extends AppCompatActivity {

    private LinePathView mPathView;

    private Button mBtClean;

    private Button mBtSave;

    private ImageView imageView;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        initView();
        initEvent();
    }


    private void initView() {
        mPathView = findViewById(R.id.line_path_view);
        mBtClean  = findViewById(R.id.bt_clean);
        mBtSave = findViewById(R.id.bt_save);
        imageView = findViewById(R.id.iv_pic);
    }


    private void initEvent() {
        //修改背景、笔宽、颜色
        mPathView.setBackColor(Color.WHITE);
        mPathView.setPaintWidth(20);
        mPathView.setPenColor(Color.BLACK);
        //清除
        mBtClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathView.clear();
                mPathView.setBackColor(Color.WHITE);
                mPathView.setPaintWidth(20);
                mPathView.setPenColor(Color.BLACK);
            }
        });
        //保存
        mBtSave.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (mPathView.getTouched()) {
                    path = mPathView.saveBitmap();
//                    setResult(100);
                    Glide.with(SignatureActivity.this).clear(imageView);
                    Glide.with(SignatureActivity.this).load(new File(path)).into(imageView);
                } else {
                    Toast.makeText(SignatureActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        File file = new File(path);
        file.delete();
    }
}