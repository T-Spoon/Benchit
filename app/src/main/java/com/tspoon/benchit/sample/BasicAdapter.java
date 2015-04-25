package com.tspoon.benchit.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BasicAdapter<T> extends BaseAdapter {

    LayoutInflater mInflater;
    List<T> mItems;
    Context mContext;

    public BasicAdapter(Context context, List<T> items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
        mContext = context;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_benchmark, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(mItems.get(position).toString());
        return convertView;
    }

    public void addItem(T item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    class ViewHolder {
        @InjectView(R.id.item_name) TextView textView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }
    }
}
