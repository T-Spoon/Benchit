package com.tspoon.benchit.sample.data;

import java.util.ArrayList;

public class Data {

    private static Data sInstance;
    private static final int LIST_SIZE = 1000000;

    private ArrayList<Integer> sampleArrayList;

    private Data() {
        sampleArrayList = new ArrayList<Integer>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++) {
            sampleArrayList.add(i);
        }
    }

    public static Data getInstance() {
        if (sInstance == null) {
            sInstance = new Data();
        }
        return sInstance;
    }

    public ArrayList<Integer> getSampleArrayList() {
        return sampleArrayList;
    }
}
