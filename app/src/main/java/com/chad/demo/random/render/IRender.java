package com.chad.demo.random.render;

import android.graphics.Canvas;

import com.chad.demo.random.model.IDrawModel;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public interface IRender {

    void setDrawModel(IDrawModel model);

    void setCanvasSize(int width, int height);

    void render(Canvas canvas, long time);
}
