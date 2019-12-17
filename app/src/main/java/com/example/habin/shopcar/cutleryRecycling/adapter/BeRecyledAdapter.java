package com.example.habin.shopcar.cutleryRecycling.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.R;

public class BeRecyledAdapter extends MyBaseAdapter {

    public BeRecyledAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToBeRecyledAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_recyle, null);
            viewHolder = new ToBeRecyledAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ToBeRecyledAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
