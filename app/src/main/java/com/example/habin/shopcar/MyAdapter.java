package com.example.habin.shopcar;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.habin.shopcar.gson.goods;
import com.example.habin.shopcar.gson.items;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Create by HABIN on 2019/11/1911:35
 * Email:739115041@qq.com
 */
public class MyAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<goods> mgoodList;
    private List<items> mitemList;
    private LayoutInflater layoutInflater;
    private final String TAG = "Adadper";

    public MyAdapter(Context context, List<items> itemList) {
        this.mContext = context;
        this.mitemList = itemList;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 自定义设置数据方法；
     * 通过notifyDataSetChanged()刷新数据，可保持当前位置
     *
     * @param itemList 需要刷新的数据
     */
    public void setData(List<items> itemList) {
        this.mitemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (mitemList != null && mitemList.size() > 0) {
            return mitemList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mitemList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.shop_item_title, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        final items item = mitemList.get(groupPosition);
        String shopName = item.getShopName();
        if (shopName != null) {
            groupViewHolder.shop_name.setText(shopName);
        } else {
            groupViewHolder.shop_name.setText("");
        }//判断商品Checkbox是否全部选中 并设置
        boolean currentSellerAllGoodSelected = isCurrentSellerAllGoodSelected(groupPosition);
        groupViewHolder.check_box_shopname.setChecked(currentSellerAllGoodSelected);

        //D.设置点击商家CheckBox
        groupViewHolder.check_box_shopname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCartListChangeListener != null) {
                    mOnCartListChangeListener.onSellerCheckedChange(groupPosition);
                }
            }
        });
        return convertView;
    }

    //组
    static class GroupViewHolder {

        private CheckBox check_box_shopname;
        private TextView shop_name;

        GroupViewHolder(View view) {
            check_box_shopname = view.findViewById(R.id.check_box_shopname);
            shop_name = view.findViewById(R.id.shop_name);
        }
    }

    /*------------子item------------*/
    @Override
    public int getChildrenCount(int groupPosition) {
        if (mitemList.get(groupPosition).getGoodsList() != null && mitemList.get(groupPosition).getGoodsList().size() > 0) {
            return mitemList.get(groupPosition).getGoodsList().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mitemList.get(groupPosition).getGoodsList().get(childPosition);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.shop_item, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final goods good = mitemList.get(groupPosition).getGoodsList().get(childPosition);

        //加载图片
        Glide.with(mContext).load(good.getImage()).into(childViewHolder.iv_adapter_list_pic);
        //设置商品名
        childViewHolder.tv_goods_name.setText(good.getGoodsName());
        //复选框是否选中
        childViewHolder.check_box.setChecked(good.isCheck());
        //商品价格
        childViewHolder.tv_goods_money.setText("￥" + good.getMoney());
        //判断是否有优惠
        if (good.getPrice() != good.getMoney()) {
            childViewHolder.tv_goods_price.setText("￥" + good.getPrice());
            childViewHolder.tv_goods_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            childViewHolder.tv_goods_price.setVisibility(View.INVISIBLE);
        }
        childViewHolder.tv_num.setText("" + good.getNumber());
        //child商品CheckBox点击事件，通过接口回调，暴露在外面
        childViewHolder.check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCartListChangeListener != null) {
                    mOnCartListChangeListener.onProductCheckedChange(groupPosition, childPosition);
                }
            }
        });
        //商品数量减监听
        childViewHolder.tv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strnum = childViewHolder.tv_num.getText().toString();
                int num = Integer.parseInt(strnum);
                if (num > 1) {
                    strnum = "" + --num;
                    //商品数量减
//                    good.setNumber(num);
                    childViewHolder.tv_num.setText(strnum);
                }
                mOnCartListChangeListener.onProducNumberChange(groupPosition, childPosition, strnum);
            }
        });
        //商品数据加监听
        childViewHolder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strnum = childViewHolder.tv_num.getText().toString();
                int num = Integer.parseInt(strnum);
                strnum = "" + ++num;
                childViewHolder.tv_num.setText(strnum);
                mOnCartListChangeListener.onProducNumberChange(groupPosition, childPosition, strnum);
            }
        });
        return convertView;
    }


    static class ChildViewHolder {
        private CheckBox check_box;
        private ImageView iv_adapter_list_pic;
        private TextView tv_goods_name;
        private TextView tv_goods_money;
        private TextView tv_goods_price;
        private TextView tv_reduce;
        private TextView tv_num;
        private TextView tv_add;

        ChildViewHolder(View view) {
            check_box = view.findViewById(R.id.check_box);
            iv_adapter_list_pic = view.findViewById(R.id.iv_adapter_list_pic);
            tv_goods_name = view.findViewById(R.id.tv_goods_name);
            tv_goods_money = view.findViewById(R.id.tv_goods_money);
            tv_goods_price = view.findViewById(R.id.tv_goods_price);
            tv_reduce = view.findViewById(R.id.tv_reduce);
            tv_num = view.findViewById(R.id.tv_num);
            tv_add = view.findViewById(R.id.tv_add);
        }
    }

    /**
     * -----------------方法-----------------
     **/
    /*根据商品改变商家--如果商品全部选中商家则选中--有一个商品没选中则商家不选中*/
    public boolean isCurrentSellerAllGoodSelected(int groupPosition) {
        items item = mitemList.get(groupPosition);
        List<goods> goods = item.getGoodsList();
        for (goods good : goods) {
//            Log.d("CheckBox", "isCurrentSellerAllProductSelected: "+good.isCheck());
            if (!good.isCheck()) {//只要有一个是false就返回false
                return false;
            }
        }
        return true;
    }

    //根据商品改变全选--如果商品全部选中全选则选中--有一个商品没选中则全选不选中
    public boolean isAllGoodsSelected() {
        for (int x = 0; x < mitemList.size(); x++) {
            items item = mitemList.get(x);
            List<goods> goods = item.getGoodsList();
            for (int j = 0; j < goods.size(); j++) {
                if (!goods.get(j).isCheck()) {
                    return false;
                }
            }
        }
        return true;
    }

    //计算总数量
    public int calculateTotalNumber() {
        int totalNumber = 0;
        for (int i = 0; i < mitemList.size(); i++) {
            items item = mitemList.get(i);
            List<goods> goods = item.getGoodsList();
            for (int j = 0; j < goods.size(); j++) {
//                Log.d(TAG, "calculateTotalNumber: + isCheck "+i+"=="+
//                        goods.get(j).isCheck());
                if (goods.get(j).isCheck() != false) {
                    int num = goods.get(j).getNumber();
                    totalNumber += num;
                }
            }
        }
        return totalNumber;

    }

    //获取总价
    public float calculateTotalPrice() {
        float totalPrice = 0;
        for (int i = 0; i < mitemList.size(); i++) {
            items item = mitemList.get(i);
            List<goods> goods = item.getGoodsList();
            for (int j = 0; j < goods.size(); j++) {
                if (goods.get(j).isCheck() != false) {
                    float money = goods.get(j).getMoney();
                    float price = goods.get(j).getPrice();
                    //如果有折扣 且不高于原价
                    if (money != price && money > price) {
                        money = price;
                    }
                    int num = goods.get(j).getNumber();
                    totalPrice += money * num;
                }
            }
        }
        return totalPrice;
    }


    //C.当商品组的全选框点击时,更新所有商品的状态
    public void changeCurrentSellerAllGoodStatus(int groupPosition, boolean isSelected) {

        items item = mitemList.get(groupPosition);
        List<goods> goods = item.getGoodsList();
        for (int j = 0; j < goods.size(); j++) {
            goods good = goods.get(j);
            good.setCheck(isSelected);
        }
    }

    //C.当商家子条目的全选框选中时,更新其状态
    public void changeCurrentGoodStatus(int groupPosition, int childPosition) {
        items item = mitemList.get(groupPosition);
        List<goods> goods = item.getGoodsList();
        goods good = goods.get(childPosition);
        good.setCheck(!good.isCheck());

    }

    //C.设置所有商品的状态
    public void changeAllGoodStatus(boolean selected) {
        for (int x = 0; x < mitemList.size(); x++) {
            items items = mitemList.get(x);
            List<goods> good = items.getGoodsList();
            for (int j = 0; j < good.size(); j++) {
                good.get(j).setCheck(selected);
            }
        }
    }

    //C.当加减器被点击时,调用,改变里面当前商品的数量 参数1定位那个商家  参数2定位哪个商品 参数3定位改变具体的数量是多少
    public void changeCurrentGoodNumber(int groupPosition, int childPosition, int number) {
        items items = mitemList.get(groupPosition);
        List<goods> goods = items.getGoodsList();
        goods good = goods.get(childPosition);
        good.setNumber(number);
    }

    /**
     * ---------------------回调接口-------------------------------
     */
    //D.
    private onCartListChangeListener mOnCartListChangeListener;

    //D.
    public void setOnCartListChangeListener(onCartListChangeListener onCartListChangeListener) {
        mOnCartListChangeListener = onCartListChangeListener;
    }

    public interface onCartListChangeListener {
        /**
         * 当商家的checkBox点击时回调
         */
        void onSellerCheckedChange(int groupPosition);

        /**
         * 当点击子条目商品的CheckBox回调
         */
        void onProductCheckedChange(int groupPosition, int childPosition);

        /**
         * 当点击加减按钮的回调
         */
        void onProducNumberChange(int groupPosition, int childPosition, String number);

    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
