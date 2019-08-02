package com.chad.demo.random.util;

import android.util.Log;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public class Logger {

    private static int sLevel = Log.VERBOSE;

    private static boolean checkLevel(int level) {
        return level >= sLevel;
    }

    public static void e(String model, String tag, String format, Object...args) {
        if (!checkLevel(Log.ERROR)) {
            return;
        }
        Log.e(String.format("%s.%s", model, tag), String.format(format, args));
    }

    public static void w(String model, String tag, String format, Object...args) {
        if (!checkLevel(Log.WARN)) {
            return;
        }
        Log.w(String.format("%s.%s", model, tag), String.format(format, args));
    }

    public static void d(String model, String tag, String format, Object...args) {
        if (!checkLevel(Log.DEBUG)) {
            return;
        }
        Log.d(String.format("%s.%s", model, tag), String.format(format, args));
    }

    public static void i(String model, String tag, String format, Object...args) {
        if (!checkLevel(Log.INFO)) {
            return;
        }
        Log.i(String.format("%s.%s", model, tag), String.format(format, args));
    }
}
