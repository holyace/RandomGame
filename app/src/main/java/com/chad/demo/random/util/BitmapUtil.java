package com.chad.demo.random.util;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-22.
 */
public class BitmapUtil {

    private static final String TAG = BitmapUtil.class.getSimpleName();

    public static float scale(int bw, int bh, int ww, int wh, int mode) {

        float scale = 1f;
        if (bw < ww || bh < wh) {
            float sx = ww / (float)bw;
            float sy = wh / (float)bh;

            if (sx > sy) {
                scale = sx;
            }
            else {
                scale = sy;
            }
        }
        else {
            float sx = ww / (float)bw;
            float sy = wh / (float)bh;

            if (sx > sy) {
                scale = sx;
            }
            else {
                scale = sy;
            }
        }
        return scale;
    }

}
