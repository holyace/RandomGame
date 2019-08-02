package com.chad.demo.random.constant;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public enum Direction {

    UP("UP"),
    RIGHT("RIGHT"),
    DOWN("DOWN"),
    LEFT("LEFT");

    private String msg;

    Direction(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
