package com.tspoon.benchit.sample.comparisons;

import com.tspoon.benchit.Benchit;
import com.tspoon.benchit.Result;
import com.tspoon.benchit.sample.Config;

import java.util.ArrayList;

public abstract class Benchmark {

    ArrayList<Result> mResults;

    public void setup() {
        mResults = new ArrayList<>();
    }

    public final void runBenchmarks(Benchit.Precision precision) {
        String name = getBenchmarkName();
        for (int i = 0; i < Config.NUM_TESTS; i++) {
            Benchit.begin(name);
            benchmark();
            Benchit.end(name).precision(precision);
        }
        mResults.add(Benchit.analyze(name).log());
    }

    public abstract void benchmark();

    public String getBenchmarkName() {
        return getClass().getSimpleName();
    }

    public ArrayList<Result> getResults() {
        return mResults;
    }

}
