package com.tspoon.benchit.sample.comparisons;

import com.tspoon.benchit.sample.data.Data;

import java.util.ArrayList;

public class ArrayListComparison extends Comparison {


    @Override
    public void setup() {
        mBenchmarks = new ArrayList<Benchmark>();
        mBenchmarks.add(new BenchmarkArrayListNaive());
        mBenchmarks.add(new BenchmarkArrayListEnhanced());
        mBenchmarks.add(new BenchmarkArrayListFast());
    }

    class BenchmarkArrayListNaive extends Benchmark {

        private ArrayList<Integer> mData = Data.getInstance().getSampleArrayList();

        @Override
        public void benchmark() {
            for (int i = 0; i < mData.size(); i++) {
                Object o = mData.get(i);
            }
        }
    }

    class BenchmarkArrayListFast extends Benchmark {

        private ArrayList<Integer> mData = Data.getInstance().getSampleArrayList();

        @Override
        public void benchmark() {
            int size = mData.size();
            for (int i = 0; i < size; i++) {
                Object o = mData.get(i);
            }
        }
    }

    class BenchmarkArrayListEnhanced extends Benchmark {

        private ArrayList<Integer> mData = Data.getInstance().getSampleArrayList();

        @Override
        public void benchmark() {
            for (Object o : mData) {

            }
        }
    }
}
