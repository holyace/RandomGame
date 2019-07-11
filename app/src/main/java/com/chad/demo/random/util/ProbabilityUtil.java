package com.chad.demo.random.util;

import java.util.Random;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-11.
 */
public class ProbabilityUtil {

    private static Random sRandom = new Random();

    public static int random(float[] rates) {
        float r = sRandom.nextFloat();
        float sum = 0;
        for (int i = 0; i < rates.length; i++) {
            sum += rates[i];
            if (r < sum) {
                return i;
            }
        }
        return -1;
    }

}
