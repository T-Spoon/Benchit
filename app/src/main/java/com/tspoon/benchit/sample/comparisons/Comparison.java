package com.tspoon.benchit.sample.comparisons;

import android.util.Log;

import java.util.List;

public abstract class Comparison {

    private static final String TAG = "Comparison";

    List<Benchmark> mBenchmarks;

    public abstract void setup();

    public final void runComparisons() {
        Log.d(TAG, "Beginning Comparison: " + getName());
        for (Benchmark benchmark : mBenchmarks) {
            benchmark.setup();
            benchmark.runBenchmarks();
        }
        Log.d(TAG, "End Comparison: " + getName());
    }

    public String getName() {
        return getClass().getSimpleName();
    }
}
