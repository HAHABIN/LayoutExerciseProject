package com.example.habin.shopcar.salaryTest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.salaryTest.Entity.itemEntity;
import com.example.habin.shopcar.salaryTest.Entity.recordDetailsEntity;

import java.util.List;

public class SalaryListAdapter extends RecyclerView.Adapter<SalaryListAdapter.ViewHolder> {

    private Context mContext;
    private recordDetailsEntity.RecordData mItems;
    private List<recordDetailsEntity.RecordData.salaryRecordInfoEditVO>  mDetailList;


    public SalaryListAdapter(Context context, recordDetailsEntity.RecordData items) {
        mContext = context;
        mItems = items;
        mDetailList = items.getSalaryRecordInfoEditVOList();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        recordDetailsEntity.RecordData.salaryRecordInfoEditVO item = mDetailList.get(position);
        holder.mTvName.setText(item.getFieldName());
        holder.mTvNum.setText(item.getFieldValue());
    }

    @Override
    public int getItemCount() {
        return mDetailList == null ? 0 : mDetailList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvNum;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvNum = itemView.findViewById(R.id.tv_num);
        }
    }
}
