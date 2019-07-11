package com.chad.demo.random.util;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public enum Direction {

    GO_UP,
    GO_RIGHT,
    GO_BOTTOM,
    GO_LEFT;

    public Direction valueOf(int val) {
        int len = Direction.values().length;
        int index = val % len;
        if (index < 0) {
            index = (index + len) % len;
        }
        return Direction.values()[index];
    }

    public Direction turn(Turn turn) {
        switch (turn) {
            case TURN_BACK:
                return valueOf(this.ordinal() + 2);

            case TURN_LEFT:
                return valueOf(this.ordinal() - 1);

            case TURN_RIGHT:
                return valueOf(this.ordinal() + 1);
            case KEEP:
                default:
                    return this;
        }
    }
}
