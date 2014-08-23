package com.tspoon.benchit.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tspoon.benchit.sample.comparisons.Comparison;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BenchmarkListAdapter extends BasicAdapter<Comparison> {

    private Context mContext;

    public BenchmarkListAdapter(Context context, List<Comparison> comparisons) {
        super(context, comparisons);
        mContext = context;
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

        Comparison benchmark = getItem(position);
        holder.textView.setText(benchmark.getName());
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.item_name) TextView textView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }
    }
}
