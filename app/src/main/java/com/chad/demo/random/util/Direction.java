package com.chad.demo.random.util;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public enum Direction {

    GO_UP("GO_UP"),
    GO_RIGHT("GO_RIGHT"),
    GO_DOWN("GO_DOWN"),
    GO_LEFT("GO_LEFT");

    private String msg;

    Direction(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
