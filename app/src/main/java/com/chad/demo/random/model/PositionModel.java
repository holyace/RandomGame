package com.chad.demo.random.model;

import android.graphics.PointF;

import com.chad.demo.random.util.Direction;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-11.
 */
public class PositionModel {

    private PointF position;

    private Direction direction = Direction.GO_RIGHT;

    public PositionModel() {
        position = new PointF();
    }

    public PositionModel(float x, float y, Direction direction) {
        setPosition(x, y);
        direction = direction;
    }

    public PointF getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPosition(float x, float y) {
        if (position == null) {
            position = new PointF();
        }
        position.set(x, y);
    }

    public void setPosition(PointF point) {
        if (point == null) {
            return;
        }
        setPosition(point.x, point.y);
    }
}
