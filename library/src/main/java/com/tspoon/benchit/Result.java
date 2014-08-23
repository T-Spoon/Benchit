package com.tspoon.benchit;

import java.util.ArrayList;

public class Result {
    String tag;
    ArrayList<Long> times;
    Benchit.Precision precision;
    float average;

    Result(Benchmark benchmark) {
        tag = benchmark.tag;
        times = benchmark.times;
        precision = benchmark.precision;
        average();
    }

    void average() {
        int size = times.size();
        long total = 0;
        for (int i = 0; i < size; i++) {
            total += times.get(i);
        }
        average = total / (float) size;
    }

    public Result log() {
        Benchit.log("Average", tag, Math.round(average / precision.divider) + precision.unit);
        return this;
    }
}