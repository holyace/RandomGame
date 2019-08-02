package com.chad.demo.random.constant;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public enum  Turn {

    KEEP("KEEP"),
    FORCE_TURN("FORCE_TURN"),
    TURN_LEFT("TURN_LEFT"),
    TURN_RIGHT("TURN_RIGHT"),
    TURN_BACK("TURN_BACK");

    private String msg;

    Turn(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
