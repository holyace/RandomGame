package com.chad.demo.random.render;

import android.graphics.Canvas;

import com.chad.demo.random.model.IRobot;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public interface IRender {

    void setRobot(IRobot model);

    void setCanvasSize(int width, int height);

    void render(Canvas canvas, long time);
}
