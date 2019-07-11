package com.chad.demo.random.model;


import android.graphics.Path;

import com.chad.demo.random.mgr.MovePolicy;
import com.chad.demo.random.util.Direction;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-11.
 */
public class MoveModel {

    private PositionModel old;
    private PositionModel now;

    private Path path;

    public MoveModel() {
        now = new PositionModel();
    }

    public void setPosition(float x, float y, Direction direction) {
        if (now == null) {
            now = new PositionModel();
        }
        now.setPosition(x, y);
        now.setDirection(direction);
    }

    public PositionModel getOld() {
        return old;
    }

    public void setOld(PositionModel old) {
        this.old = old;
    }

    public PositionModel getNow() {
        return now;
    }

    public void setNow(PositionModel now) {
        this.now = now;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
