package com.tspoon.benchit.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BasicAdapter<T> extends BaseAdapter {

    LayoutInflater mInflater;
    List<T> mItems;

    public BasicAdapter(Context context, List<T> items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_benchmark, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.item_name);
        textView.setText(mItems.get(position).toString());
        return convertView;
    }

    public void addItem(T item) {
        mItems.add(item);
        notifyDataSetChanged();
    }
}
