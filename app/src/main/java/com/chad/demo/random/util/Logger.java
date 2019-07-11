package com.chad.demo.random.util;

import android.util.Log;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public class Logger {

    public static void e(String tag, String format, Object...args) {
        Log.e(tag, String.format(format, args));
    }
}
