package com.example.habin.shopcar.cutleryRecycling.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.habin.shopcar.R;

public class BeRecyledAdapter extends MyBaseAdapter {

    private String mStatus;

    public BeRecyledAdapter(Context context , String status) {
        super(context);
        this.mStatus = status==null ? null : status;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_recyle, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (mStatus.equals("8")){
            viewHolder.btnConfirm.setVisibility(View.VISIBLE);
            viewHolder.llTailEnd.setVisibility(View.GONE);
            viewHolder.itRecylable.setVisibility(View.VISIBLE);
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
        }
    }
}
