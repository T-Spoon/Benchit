package com.tspoon.benchit;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Benchit {

    public static final String TAG = "Benchit";

    static Benchit.Precision DEFAULT_PRECISION = Benchit.Precision.MILLI;
    static Set<Stat> STATISTICS = new HashSet<>();

    private static Benchit singleton;

    private HashMap<String, Long> starts;
    private HashMap<String, Benchmark> benchmarks;

    Benchit() {
        starts = new HashMap<>();
        benchmarks = new HashMap<>();
        Benchit.setEnabledStats(Stat.AVERAGE, Stat.RANGE, Stat.STANDARD_DEVIATION);
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

    public static Benchit clear() {
        if (singleton == null) {
            singleton = get();
        }
        return singleton.clearInternal();
    }

    public static ComparisonResult compare(Stat orderBy, Order order, String... tags) {
        if (singleton == null) {
            singleton = get();
        }
        return singleton.compareInternal(orderBy, order, tags);
    }

    public static ComparisonResult compare(Stat orderBy, String... tags) {
        return compare(orderBy, Order.ASCENDING, tags);
    }

    public static void setDefaultPrecision(Precision precision) {
        if (precision != null) {
            DEFAULT_PRECISION = precision;
        }
    }

    public static void setEnabledStats(Stat... stats) {
        STATISTICS = new HashSet<>();
        Collections.addAll(STATISTICS, stats);
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

    private Benchit clearInternal() {
        starts.clear();
        benchmarks.clear();
        return this;
    }

    private ComparisonResult compareInternal(Stat orderBy, Order order, String... tags) {
        ArrayList<Result> results = new ArrayList<>();

        if (tags.length == 0) {
            for (String tag : benchmarks.keySet()) {
                results.add(analyzeInternal(tag));
            }
        } else {
            for (String tag : tags) {
                results.add(analyzeInternal(tag));
            }
        }

        return new ComparisonResult(orderBy, order, results);
    }

    static void log(String message) {
        Log.d(TAG, message);
    }

    static void log(String type, String tag, String result) {
        String log = String.format("%s [%s] --> %s", type, tag, result);
        Log.d(TAG, log);
    }

    static void logMany(String tag, List<Pair<String, String>> stats) {
        StringBuilder sb = new StringBuilder("[" + tag + "] --> ");

        for (Pair<String, String> stat : stats) {
            sb.append(String.format("%s[%s], ", stat.first, stat.second));
        }
        sb.delete(sb.length() - 2, sb.length() - 1);

        Log.d(TAG, sb.toString());
    }

    public static enum Stat {
        AVERAGE, RANGE, STANDARD_DEVIATION
    }

    public static enum Order {
        ASCENDING, DESCENDING
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
