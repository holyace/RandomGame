package com.chad.demo.random.util;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-08-01.
 */
public abstract class SafeRunnable implements Runnable {

    @Override
    public void run() {
        try {
            safeRun();
        }
        catch (Throwable t) {
           t.printStackTrace();
        }
        finally {
            try {
                doLast();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void doLast() {

    }
    public abstract void safeRun();
}
