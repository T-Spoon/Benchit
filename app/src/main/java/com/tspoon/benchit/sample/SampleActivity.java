package com.tspoon.benchit.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tspoon.benchit.sample.comparisons.ArrayListComparison;
import com.tspoon.benchit.sample.comparisons.Comparison;
import com.tspoon.benchit.sample.comparisons.InternalGetterComparison;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SampleActivity extends ActionBarActivity {

    @InjectView(R.id.benchmarks_list) ListView mList;

    private BasicAdapter mAdapter;
    private ResultHandler mResultHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.inject(this);

        mResultHandler = new ResultHandler();

        final ArrayList<Comparison> comparisons = getComparisons();
        //mAdapter = new BenchmarkListAdapter(this, getComparisons());
        mAdapter = new BasicAdapter<>(this, new ArrayList<String>());
        mList.setAdapter(mAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                LogCat logCat = new LogCat(mResultHandler);
                logCat.start();


                for (int i = 0; i < comparisons.size(); i++) {
                    Comparison c = comparisons.get(i);
                    c.setup();
                    c.runComparisons();
                }
            }
        }).start();

    }

    private ArrayList<Comparison> getComparisons() {
        ArrayList<Comparison> items = new ArrayList<Comparison>();
        items.add(new ArrayListComparison());
        items.add(new InternalGetterComparison());
        return items;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ResultHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            mAdapter.addItem(msg.obj);
        }
    }
}
