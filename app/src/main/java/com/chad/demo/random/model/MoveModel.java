package com.chad.demo.random.model;


import android.graphics.Path;
import android.graphics.PointF;

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

    private float speed = 30;

    private int width;
    private int height;

    public MoveModel() {
        now = new PositionModel();
        old = new PositionModel();
        path = new Path();
    }

    public void setRange(int w, int h) {
        width = w;
        height = h;
    }

    public void setPosition(float x, float y, Direction direction) {
        if (now == null) {
            now = new PositionModel();
        }
        now.setPosition(x, y);
        now.setDirection(direction);
        path.moveTo(x, y);
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
        if (path == null) {
            path = new Path();
            if (getNow() != null) {
                path.moveTo(getNow().getPosition().x, getNow().getPosition().y);
            }
        }
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void swap() {
        if (old == null) {
            old = new PositionModel();
        }
        old.setPosition(now.getPosition());
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void turnLeft() {

        switch (now.getDirection()) {
            case GO_LEFT:
                now.setDirection(Direction.GO_DOWN);
                goDown(Direction.GO_LEFT);
                break;

            case GO_UP:
                now.setDirection(Direction.GO_LEFT);
                goLeft(Direction.GO_UP);
                break;

            case GO_RIGHT:
                now.setDirection(Direction.GO_UP);
                goUp(Direction.GO_RIGHT);
                break;

            case GO_DOWN:
                now.setDirection(Direction.GO_RIGHT);
                goRight(Direction.GO_DOWN);
                break;
        }
    }

    public void turnRight() {
        switch (now.getDirection()) {
            case GO_LEFT:
                now.setDirection(Direction.GO_UP);
                goUp(Direction.GO_LEFT);
                break;

            case GO_UP:
                now.setDirection(Direction.GO_RIGHT);
                goRight(Direction.GO_UP);
                break;

            case GO_RIGHT:
                now.setDirection(Direction.GO_DOWN);
                goDown(Direction.GO_RIGHT);
                break;

            case GO_DOWN:
                now.setDirection(Direction.GO_LEFT);
                goLeft(Direction.GO_DOWN);
                break;
        }
    }

    public void turnBack() {
        switch (now.getDirection()) {
            case GO_LEFT:
                now.setDirection(Direction.GO_RIGHT);
                goRight(null);
                break;

            case GO_UP:
                now.setDirection(Direction.GO_DOWN);
                goDown(null);
                break;

            case GO_RIGHT:
                now.setDirection(Direction.GO_LEFT);
                goLeft(null);
                break;

            case GO_DOWN:
                now.setDirection(Direction.GO_UP);
                goUp(null);
                break;
        }
    }

    public void goAhead() {

        swap();

        PointF pos = now.getPosition();

        switch (now.getDirection()) {
            case GO_LEFT:
                pos.x -= speed;
                if (pos.x < 0) {
                    pos.x = 0;
                }
                break;

            case GO_UP:
                pos.y -= speed;
                if (pos.y < 0) {
                    pos.y = 0;
                }
                break;

            case GO_RIGHT:
                pos.x += speed;
                if (pos.x > width) {
                    pos.x = width;
                }
                break;

            case GO_DOWN:
                pos.y += speed;
                if (pos.y > height) {
                    pos.y = height;
                }
                break;

        }

        path.lineTo(pos.x, pos.y);
    }

    public void goLeft(Direction od) {

        swap();

        PointF pos = now.getPosition();
        PointF op = this.old.getPosition();

        pos.x -= speed;
        if (pos.x < 0) {
            pos.x = 0;
        }

        if (od == Direction.GO_UP) {
            pos.y -= speed;
            if (pos.y < 0) {
                pos.y = 0;
            }

            path.quadTo(op.x - (pos.x - op.x) / 3f,
                    pos.y + (op.y - pos.y) / 3f,
                    pos.x, pos.y);
        }
        else if (od == Direction.GO_DOWN) {
            pos.y += speed;
            if (pos.y > height) {
                pos.y = height;
            }

            path.quadTo(op.x - (op.x - pos.x) / 3f,
                    pos.y - (pos.y - op.y) / 3f,
                    pos.x, pos.y);
        }
        else {
            path.lineTo(pos.x, pos.y);
        }
    }

    public void goRight(Direction od) {

        swap();

        PointF pos = now.getPosition();
        PointF op = old.getPosition();

        pos.x += speed;
        if (pos.x > width) {
            pos.x = width;
        }

        if (od == Direction.GO_UP) {
            pos.y -= speed;
            if (pos.y < 0) {
                pos.y = 0;
            }

            path.quadTo(op.x + (pos.x - op.x) / 3f,
                    pos.y + (op.y - pos.y) / 3f,
                    pos.x, pos.y);
        }
        else if (od == Direction.GO_DOWN) {
            pos.y += speed;
            if (pos.y > height) {
                pos.y = height;
            }

            path.quadTo(op.x + (pos.x - op.x) / 3f,
                    pos.y - (pos.y - op.y) / 3f,
                    pos.x, pos.y);
        }
        else {
            path.lineTo(pos.x, pos.y);
        }
    }

    public void goUp(Direction od) {

        swap();

        PointF pos = now.getPosition();
        PointF op = old.getPosition();

        pos.y -= speed;
        if (pos.y < 0) {
            pos.y = 0;
        }

        if (od == Direction.GO_LEFT) {
            pos.x -= speed;
            if (pos.x < 0) {
                pos.x = 0;
            }

            path.quadTo(pos.x + (op.x - pos.x) / 3f,
                    op.y - (op.y - pos.y) / 3f,
                    pos.x, pos.y);
        }
        else if (od == Direction.GO_RIGHT) {
            pos.x += speed;
            if (pos.x > width) {
                pos.x = width;
            }

            path.quadTo(pos.x - (pos.x - op.x) / 3f,
                    op.y - (op.y - pos.y) / 3f,
                    pos.x, pos.y);
        }
        else {
            path.lineTo(pos.x, pos.y);
        }
    }

    public void goDown(Direction od) {

        swap();

        PointF pos = now.getPosition();
        PointF op = old.getPosition();

        pos.y += speed;
        if (pos.y > height) {
            pos.y = height;
        }

        if (od == Direction.GO_LEFT) {
            pos.x -= speed;
            if (pos.x < 0) {
                pos.x = 0;
            }

            path.quadTo(pos.x + (op.x - pos.x) / 3f,
                    op.y + (pos.y - op.y) / 3f,
                    pos.x, pos.y);
        }
        else if (od == Direction.GO_RIGHT) {
            pos.x += speed;
            if (pos.x > width) {
                pos.x = width;
            }

            path.quadTo(pos.x - (pos.x - op.x) / 3f,
                    op.y + (pos.y - op.y) / 3f,
                    pos.x, pos.y);
        }
        else {
            path.lineTo(pos.x, pos.y);
        }
    }
}
