package com.tspoon.benchit;

import android.util.Log;

import java.util.HashMap;

public class Benchit {

    public static final String TAG = "BenchIt";
    private static Benchit singleton;

    private HashMap<String, Long> starts;
    private HashMap<String, Benchmark> benchmarks;

    Benchit() {
        starts = new HashMap<String, Long>();
        benchmarks = new HashMap<String, Benchmark>();
    }

    private static Benchit get() {
        if (singleton == null) {
            singleton = new Benchit();
        }
        return singleton;
    }

    public static Benchit begin(String tag) {
        if (singleton == null) {
            singleton = get();
        }
        return singleton.beginInternal(tag);
    }

    public static Benchmark end(String tag) {
        if (singleton == null) {
            singleton = get();
        }
        return singleton.endInternal(tag);
    }

    public static Result analyze(String tag) {
        if (singleton == null) {
            singleton = get();
        }
        return singleton.analyzeInternal(tag);
    }

    public static ComparisonResult compare(String... tags) {
        if (singleton == null) {
            singleton = get();
        }
        return singleton.compareInternal(tags);
    }

    private Benchit beginInternal(String tag) {
        starts.put(tag, System.nanoTime());
        return this;
    }

    private Benchmark endInternal(String tag) {
        long end = System.nanoTime();
        long start = starts.get(tag);
        long taken = end - start;

        Benchmark benchmark = benchmarks.get(tag);
        if (benchmark == null) {
            benchmark = new Benchmark(tag, taken);
            benchmarks.put(tag, benchmark);
        } else {
            benchmark.add(taken);
        }
        return benchmark;
    }

    private Result analyzeInternal(String tag) {
        Benchmark benchmark = benchmarks.get(tag);
        return benchmark.result();
    }

    private ComparisonResult compareInternal(String... tags) {

        return new ComparisonResult();
    }

    static void log(String type, String tag, String result) {
        String log = String.format("%s [%s] --> %s", type, tag, result);
        Log.d(TAG, log);
    }


    public static enum Precision {
        NANO("ns", 1),
        MICRO("µs", 1000),
        MILLI("ms", 1000000),
        SECOND("s", 1000000000);

        String unit;
        int divider;

        Precision(String unit, int divider) {
            this.unit = unit;
            this.divider = divider;
        }
    }
}
