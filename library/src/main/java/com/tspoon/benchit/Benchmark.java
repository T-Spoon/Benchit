package com.tspoon.benchit;

import java.util.ArrayList;

public class Benchmark {
    String tag;
    ArrayList<Long> times;
    Benchit.Precision precision;

    Benchmark(String tag, long time) {
        this.tag = tag;
        times = new ArrayList<>();
        times.add(time);
        precision = Benchit.DEFAULT_PRECISION;
    }

    public Benchmark precision(Benchit.Precision precision) {
        this.precision = precision;
        return this;
    }

    public void log() {
        Benchit.log("Result", tag, Math.round(times.get(times.size() - 1) / precision.divider) + precision.unit);
    }

    Result result() {
        return new Result(this);
    }

    void add(long time) {
        times.add(time);
    }
}