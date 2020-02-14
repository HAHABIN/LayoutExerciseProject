package com.example.habin.shopcar.btnNav;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habin.shopcar.BaseActivity;
import com.example.habin.shopcar.R;
import com.example.habin.shopcar.btnNav.fragment.HomePageFragment;
import com.example.habin.shopcar.btnNav.fragment.MessagePageFragment;
import com.example.habin.shopcar.btnNav.fragment.MinePageFragment;
import com.example.habin.shopcar.btnNav.fragment.TypePageFragment;
import com.example.habin.shopcar.btnNav.view.PublishDialog;
import com.example.habin.shopcar.cutleryRecycling.fragment.BeRecyledFragment;
import com.example.habin.shopcar.cutleryRecycling.fragment.MyRecyleFragment;
import com.example.habin.shopcar.cutleryRecycling.fragment.ToBeRecyledFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BtnNavActivity extends BaseActivity {


    @BindView(R.id.iv_home)
    ImageView mIvHome;
    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.iv_type)
    ImageView mIvType;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.iv_mine)
    ImageView mIvMine;
    @BindView(R.id.tv_mine)
    TextView mTvMine;
    //底栏ImageView控件集合
    private ImageView[] mImageList;
    //底栏TextView控件集合
    private TextView[] mTextList;
    //选中位置
    private int mIsCheck = 0;
    //图片默认样式集合
    private int[] seIds = {R.mipmap.ic_home_normal,R.mipmap.ic_type_normal,R.mipmap.ic_message_normal,R.mipmap.ic_mine_normal};
    //图片选中样式集合
    private int[] noseIds = {R.mipmap.ic_home_selected,R.mipmap.ic_type_selected,R.mipmap.ic_message_selected,R.mipmap.ic_mine_select};
    //当前fragment
    private Fragment fragment;
    //发布动画
    private PublishDialog publishDialog;
    @Override
    protected int getLayout() {
        return R.layout.activity_btn_nav;
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mImageList = new ImageView[]{mIvHome,mIvType,mIvMessage,mIvMine};
        mTextList = new TextView[]{mTvHome,mTvType,mTvMessage,mTvMine};
        //默认
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new HomePageFragment()).commit();
    }



    @OnClick({R.id.iv_back,R.id.btn_add,R.id.rl_home, R.id.rl_type, R.id.rl_message, R.id.rl_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_add:
                if (publishDialog==null){
                    publishDialog=new PublishDialog(BtnNavActivity.this);
                    publishDialog.setFabuClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(BtnNavActivity.this, "发布", Toast.LENGTH_SHORT).show();
                        }
                    });
                    publishDialog.setHuishouClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(BtnNavActivity.this, "回收", Toast.LENGTH_SHORT).show();

                        }
                    });
                    publishDialog.setPingguClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(BtnNavActivity.this, "评估", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                publishDialog.show();
                break;
            case R.id.rl_home:
                mIsCheck = 0;
                fragment = HomePageFragment.newInstance();//首页
                setStaus();
                break;
            case R.id.rl_type:
                mIsCheck = 1;
                fragment =  TypePageFragment.newInstance();//分类
                setStaus();
                break;
            case R.id.rl_message:
                fragment = MessagePageFragment.newInstance();//消息
                mIsCheck = 2;
                setStaus();
                break;
            case R.id.rl_mine:
                fragment = MinePageFragment.newInstance();//我的
                mIsCheck = 3;
                setStaus();
                break;
        }


    }
    //修改状态
    public void setStaus() {
        for (int i = 0; i < mImageList.length; i++) {
            ImageView imageView = mImageList[i];
            TextView textView = mTextList[i];
            //当前图片选中，并且其他设置为默认样式
            imageView.setBackgroundResource(seIds[i]);
            textView.setTextColor(Color.parseColor("#515151"));
            if (i == mIsCheck) {
                imageView.setBackgroundResource(noseIds[i]);
                textView.setTextColor(Color.parseColor("#386FFE"));
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
    }
}
