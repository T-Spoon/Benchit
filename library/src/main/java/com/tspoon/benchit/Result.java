package com.tspoon.benchit;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Result {

    String tag;
    ArrayList<Long> times;
    Benchit.Precision precision;

    double average;
    double deviation;

    long min;
    long max;


    Result(Benchmark benchmark) {
        tag = benchmark.tag;
        times = benchmark.times;
        precision = benchmark.precision;

        average = average();
        deviation = deviation();

        long[] minMax = range();
        min = minMax[0];
        max = minMax[1];
    }

    private double average() {
        int size = times.size();
        long total = 0;
        for (int i = 0; i < size; i++) {
            total += times.get(i);
        }
        return total / (double) size;
    }

    private long[] range() {
        int size = times.size();
        long min = times.get(0);
        long max = times.get(0);
        for (int i = 0; i < size; i++) {
            long time = times.get(i);
            if (time > max) {
                max = time;
            } else if (time < min) {
                min = time;
            }
        }

        return new long[]{min, max};
    }

    private double deviation() {
        double mean = average();
        double temp = 0;

        int size = times.size();
        for (int i = 0; i < size; i++) {
            long t = times.get(i);
            temp += (mean - t) * (mean - t);
        }
        return Math.sqrt(temp / size);
    }

    double getStat(Benchit.Stat stat) {
        switch (stat) {
            case AVERAGE:
                return average;
            case STANDARD_DEVIATION:
                return deviation;
            case RANGE:
                return max - min;
            default:
                throw new IllegalArgumentException("Stat not implemented yet: " + stat);
        }
    }

    public Result log() {
        List<Pair<String, String>> stats = new ArrayList<>();

        stats.add(new Pair<>("Sample Size", times.size() + ""));

        if (Benchit.STATISTICS.contains(Benchit.Stat.AVERAGE)) {
            stats.add(new Pair<>("Average", time(average) + precision.unit));
        }
        if (Benchit.STATISTICS.contains(Benchit.Stat.RANGE)) {
            stats.add(new Pair<>("Range", time(min) + precision.unit + " --> " + time(max) + precision.unit));
        }
        if (Benchit.STATISTICS.contains(Benchit.Stat.STANDARD_DEVIATION)) {
            stats.add(new Pair<>("Deviation", time(deviation) + precision.unit));
        }

        Benchit.logMany(tag, stats);
        return this;
    }

    private long time(long stat) {
        return Math.round(stat / (double) precision.divider);
    }

    private long time(double stat) {
        return Math.round(stat / precision.divider);
    }
}