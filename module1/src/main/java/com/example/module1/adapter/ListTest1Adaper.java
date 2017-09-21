package com.example.module1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.module1.R;

/**
 * Created by sqhan on 2017/9/21.
 */

public class ListTest1Adaper extends BaseAdapter {
    private int size;
    private Context context;
    private LayoutInflater inflater;

    public ListTest1Adaper(int size, Context context) {
        this.size = size;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int position) {
//        return list.get(position);//一般情况下为传入的list的一项
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_test1_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.tv1);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.tv2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView1.setText("item");
        viewHolder.textView2.setText("-" + (1 + position));

        return convertView;
    }

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
    }


}
