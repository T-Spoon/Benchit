package com.tspoon.benchit.sample;

import android.os.Handler;
import android.os.Message;

import com.tspoon.benchit.Benchit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogCat extends Thread {

    private Handler mHandler;

    public LogCat(Handler handler) {
        super("LogCat Reader");
        mHandler = handler;
    }

    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec("logcat -c ");
            Process process = Runtime.getRuntime().exec("logcat -s " + Benchit.TAG);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] sections = line.split(":");
                if (sections.length > 1) {
                    Message msg = Message.obtain();
                    msg.obj = sections[1];
                    mHandler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
