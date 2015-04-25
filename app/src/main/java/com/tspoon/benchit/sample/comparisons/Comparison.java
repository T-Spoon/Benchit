package com.tspoon.benchit.sample.comparisons;

import com.tspoon.benchit.Benchit;

import java.util.List;

import timber.log.Timber;

public abstract class Comparison {

    List<Benchmark> mBenchmarks;

    public abstract void setup();

    public final void runComparisons() {
        Timber.d("Begin Comparison: " + getName());
        for (Benchmark benchmark : mBenchmarks) {
            benchmark.setup();
            benchmark.runBenchmarks(getPrecision());
        }
        Timber.d("End Comparison: " + getName());
        Benchit.compare(Benchit.Stat.STANDARD_DEVIATION).log();
        Benchit.clear();
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public abstract Benchit.Precision getPrecision();
}
