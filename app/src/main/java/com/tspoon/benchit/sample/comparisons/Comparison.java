package com.tspoon.benchit.sample.comparisons;

import java.util.List;

import timber.log.Timber;

public abstract class Comparison {

    List<Benchmark> mBenchmarks;

    public abstract void setup();

    public final void runComparisons() {
        Timber.d("Begin Comparison: " + getName());
        for (Benchmark benchmark : mBenchmarks) {
            benchmark.setup();
            benchmark.runBenchmarks();
        }
        Timber.d("End Comparison: " + getName());
    }

    public String getName() {
        return getClass().getSimpleName();
    }
}
