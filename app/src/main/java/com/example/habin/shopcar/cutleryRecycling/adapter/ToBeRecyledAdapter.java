package com.example.habin.shopcar.cutleryRecycling.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.habin.shopcar.R;

public class ToBeRecyledAdapter extends MyBaseAdapter {


    public ToBeRecyledAdapter(Context context) {
        super(context);
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
        return convertView;
    }

    @Override
    public int getCount() {
        return 10;
    }

    static class ViewHolder {


        public ViewHolder(View itemView) {

        }
    }

}
