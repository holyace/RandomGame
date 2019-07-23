package com.chad.demo.random.util;

import java.util.Random;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-22.
 */
public class RandomUtil {

    private static Random sRandom;

    public static Random getRandom() {
        if (sRandom == null) {
            sRandom = new Random();
        }
        return sRandom;
    }

}
