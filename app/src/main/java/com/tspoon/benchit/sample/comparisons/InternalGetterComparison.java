package com.tspoon.benchit.sample.comparisons;

import com.tspoon.benchit.Benchit;

import java.util.ArrayList;

public class InternalGetterComparison extends Comparison {

    private static final int GETS_PER_TEST = 100000;

    @Override
    public void setup() {
        mBenchmarks = new ArrayList<>();
        mBenchmarks.add(new BenchmarkInternalAccess());
        mBenchmarks.add(new BenchmarkInternalGetter());
    }

    @Override
    public Benchit.Precision getPrecision() {
        return Benchit.Precision.MICRO;
    }


    private class BenchmarkInternalAccess extends Benchmark {

        int number = 1;

        @Override
        public void benchmark() {
            for (int j = 0; j < GETS_PER_TEST; j++) {
                int num = number;
            }
        }
    }

    private class BenchmarkInternalGetter extends Benchmark {

        int number = 1;

        @Override
        public void benchmark() {
            for (int j = 0; j < GETS_PER_TEST; j++) {
                int num = getNumber();
            }
        }

        public int getNumber() {
            return number;
        }
    }
}
