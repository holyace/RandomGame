package com.chad.demo.random.util;

import android.util.Log;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public class Logger {

    private static int sLevel = Log.INFO;

    private static boolean checkLevel(int level) {
        return level >= sLevel;
    }

    public static void e(String tag, String format, Object...args) {
        if (!checkLevel(Log.ERROR)) {
            return;
        }
        Log.e(tag, String.format(format, args));
    }

    public static void w(String tag, String format, Object...args) {
        if (!checkLevel(Log.WARN)) {
            return;
        }
        Log.w(tag, String.format(format, args));
    }

    public static void d(String tag, String format, Object...args) {
        if (!checkLevel(Log.DEBUG)) {
            return;
        }
        Log.d(tag, String.format(format, args));
    }

    public static void i(String tag, String format, Object...args) {
        if (!checkLevel(Log.INFO)) {
            return;
        }
        Log.i(tag, String.format(format, args));
    }
}
