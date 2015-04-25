package com.tspoon.benchit.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tspoon.benchit.Benchit;
import com.tspoon.benchit.sample.comparisons.ArrayListComparison;
import com.tspoon.benchit.sample.comparisons.Comparison;
import com.tspoon.benchit.sample.comparisons.InternalGetterComparison;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SampleActivity extends AppCompatActivity {

    @InjectView(R.id.benchmarks_list) ListView mList;
    @InjectView(R.id.toolbar) Toolbar mToolbar;


    private BasicAdapter mAdapter;
    private ResultHandler mResultHandler = new ResultHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);

        Benchit.setEnabledStats(Benchit.Stat.AVERAGE, Benchit.Stat.STANDARD_DEVIATION, Benchit.Stat.RANGE);
        Benchit.setDefaultPrecision(Benchit.Precision.MILLI);

        final ArrayList<Comparison> comparisons = getComparisons();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_github))));
                return true;
            case R.id.action_share:
                String message = getString(R.string.text_share, getString(R.string.url_share));
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, message);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share with..."));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ResultHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            mAdapter.addItem(msg.obj);
        }
    }
}
