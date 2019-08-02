package com.chad.demo.random.render;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;

import com.chad.demo.random.mgr.RenderManager;
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

    protected RenderManager mRenderManager;

    protected Context mContext;

    public BaseRender(RenderManager manager) {
        mPosition = new PointF();
        mRenderManager = manager;

        mContext = mRenderManager.getContext();

        initRender();
    }

    protected void initRender() {

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
