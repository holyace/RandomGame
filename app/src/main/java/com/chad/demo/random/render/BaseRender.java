package com.chad.demo.random.render;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.chad.demo.random.model.IRobot;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-22.
 */
public class BaseRender implements IRender {

    protected IRobot mRobot;

    protected int mWidth;
    protected int mHeight;

    protected PointF mPosition;

    public BaseRender() {
        mPosition = new PointF();
    }

    @Override
    public void setRobot(IRobot model) {
        mRobot = model;
    }

    @Override
    public void setCanvasSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void render(Canvas canvas, long time) {

    }
}
