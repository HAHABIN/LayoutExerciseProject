package com.example.habin.shopcar.cutleryRecycling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BeRecyledAdapter extends BaseAdapter {

    private Context mContext;
    private List<RecycleOrderListEntity.ItemBean> mDataList;
    private IitemCallback mCallback;

    //回调接口
    public interface IitemCallback {
        void callPhone(String phoneNum);

        void confirmRecycle(long orderId);
    }

    public BeRecyledAdapter(Context context,IitemCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    public BeRecyledAdapter(Context context) {
        this.mContext = context;

    }

    public void setDataList(List<RecycleOrderListEntity.ItemBean> dataList) {
        if(mDataList == null){
            mDataList = new ArrayList<>();
            mDataList.addAll(dataList);
        }else{
            mDataList.clear();
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
//        if (dataList != null) {
//            this.mDataList = dataList;
//            notifyDataSetChanged();
//        }
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_test,parent,false);
            convertView = View.inflate(mContext, R.layout.item_test, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final RecycleOrderListEntity.ItemBean itemBean = mDataList.get(position);
        //地址
        viewHolder.tvAddress.setText(itemBean.getAddress());
        //名字 + 电话
        viewHolder.tvInfo.setText(itemBean.getName() + " " + itemBean.getPhone());
        //订单号
        viewHolder.tvOrderNum.setText("订单号：" + itemBean.getOrderNum());
        //餐具
        StringBuilder sb = new StringBuilder();
        sb.append("餐具：");
        if (itemBean.getRecyclingContentJson() != null && !itemBean.getRecyclingContentJson().isEmpty()) {
            for (RecycleOrderListEntity.ItemBean.RecyclingContentJsonBean recyclingContent : itemBean.getRecyclingContentJson()) {
                sb.append(recyclingContent.getTableware())
                        .append("*")
                        .append(recyclingContent.getNumber())
                        .append("  ");
            }
        }
        viewHolder.tvCutleryNum.setText(sb);
        //判断是否为空
        if (itemBean.getStatus() != null) {
            switch (itemBean.getStatus()) {
                case "7":
                case "9":
                    //配送完成时间
                    viewHolder.tvOrderTime.setText(getDateTimeToMdHms(itemBean.getDeliveryDate()));
                    viewHolder.tvOrderTimeDes.setText(R.string.complete_time_des);
                    StringBuilder reDate = new StringBuilder();
                    reDate.append(itemBean.getRecyclingUserName())
                            .append("于")
                            .append(getDateTimeToMdHms(itemBean.getRecyclingDate()))
                            .append("完成回收");
                    viewHolder.tvTailEnd.setText(reDate);

                    break;
                case "8":
                    //下单时间
                    viewHolder.tvOrderTime.setText(getDateTimeToMdHms(itemBean.getCreateDate()));
                    viewHolder.tvOrderTimeDes.setText(R.string.order_time_des);

                    if (itemBean.getRecyclingStatus() != null&&itemBean.getRecyclingStatus().equals("1")) {
                        viewHolder.itRecylable.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.itRecylable.setVisibility(View.GONE);
                    }
                    viewHolder.btnConfirm.setVisibility(View.VISIBLE);
                    viewHolder.llTailEnd.setVisibility(View.GONE);
                    viewHolder.btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mCallback!=null) {
                                mCallback.confirmRecycle(itemBean.getId());
                            }
                        }
                    });
                    break;
            }
            viewHolder.tvBtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback!=null){
                        mCallback.callPhone(itemBean.getPhone());
                    }
                }
            });

        }


        return convertView;
    }

    static class ViewHolder {

        private TextView itRecylable;
        private LinearLayout llTailEnd;
        private TextView tvTailEnd;
        private TextView tvOrderNum;
        private TextView tvCutleryNum;
        private TextView tvBtnCall;
        private TextView tvInfo;
        private TextView tvOrderTime;
        private TextView tvAddress;
        private Button btnConfirm;
        private TextView tvOrderTimeDes;

        public ViewHolder(View itemView) {
            llTailEnd = itemView.findViewById(R.id.ll_tail_end);
            tvOrderTime = itemView.findViewById(R.id.tv_order_time);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvBtnCall = itemView.findViewById(R.id.btn_call);
            tvCutleryNum = itemView.findViewById(R.id.iv_cutlery_num);
            tvOrderNum = itemView.findViewById(R.id.tv_order_number);
            tvTailEnd = itemView.findViewById(R.id.tv_tail_end);
            btnConfirm = itemView.findViewById(R.id.btn_confirm);
            itRecylable = itemView.findViewById(R.id.tv_recyclable);
            tvOrderTimeDes = itemView.findViewById(R.id.tv_order_time_des);
        }
    }

    public static String getDateTimeToMdHms(long str) {
        try {
            Date date = new Date(str);
            SimpleDateFormat format = new SimpleDateFormat("MM.dd HH:mm:ss");
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
