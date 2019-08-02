package com.chad.demo.random.mgr;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.chad.demo.random.constant.Constant;
import com.chad.demo.random.render.IRender;
import com.chad.demo.random.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-06-28.
 */
public class RenderManager implements SurfaceHolder.Callback {

    private static final String TAG = RenderManager.class.getSimpleName();

    private static final int MAX_FPS = 60;
    private static final int MIN_FPS = 12;

    private boolean mAttached = false;
    private boolean mCreated = false;
    private boolean mPendingStart = false;
    private int mWidth;
    private int mHeight;
    private SurfaceHolder mHolder;

    private RenderThread mRenderThread;

    private Context mContext;

    private List<IRender> mRenders = new ArrayList<>();

    int mFPS = 60;
    long mTimeInteval = 1000 / mFPS;

    public void init(SurfaceView surfaceView) {
        mRenderThread = new RenderThread(this);
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);

        mAttached = true;

        mContext = surfaceView.getContext();
    }

    public void setFPS(int fps) {

        if (fps < MIN_FPS) {
            fps = MIN_FPS;
        }
        if (fps > MAX_FPS) {
            fps = MAX_FPS;
        }
        mFPS = fps;
        mTimeInteval = 1000 / mFPS;
    }

    public void addRender(IRender render) {
        if (render == null) {
            return;
        }
        mRenders.add(render);
    }

    public void removeRender(IRender render) {
        if (render == null) {
            return;
        }
        mRenders.remove(render);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Logger.i(Constant.MODULE, TAG, "surfaceCreated");

        mCreated = true;

        mHolder = holder;

        if (mPendingStart) {
            startRenderInternal();
            mPendingStart = false;
        }

        notifySurfaceCreated();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        Logger.i(Constant.MODULE, TAG, "surfaceChanged w:%d, h:%d", width, height);

        int oldw = mWidth;
        int oldh = mHeight;
        mWidth = width;
        mHeight = height;
        notifySurfaceChange(oldw, width, oldh, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        Logger.i(Constant.MODULE, TAG, "surfaceDestroyed");

        if (mHolder != null) {
            mHolder.removeCallback(this);
        }
        notifySurfaceDestory();
    }

    protected void notifySurfaceCreated() {

    }

    protected void notifySurfaceDestory() {

    }

    protected void notifySurfaceChange(int oldWidth, int newWidth, int oldHeight, int newHeight) {
        if (mRenderThread != null) {
            mRenderThread.setCanvasSize(newWidth, newHeight);
        }
    }

    protected void startRenderInternal() {
        mRenderThread.startRenderThread();
    }

    public void startRender() {
        if (!mCreated) {
            mPendingStart = true;
        }
        else {
            startRenderInternal();
            mPendingStart = false;
        }
    }

    public void stopRender() {
        mRenderThread.stopRenderThread();
    }

    public void destroyRender() {
        stopRender();
    }

    SurfaceHolder getSurfaceHolder() {
        return mHolder;
    }

    int getWidth() {
        return mWidth;
    }

    int getHeight() {
        return mHeight;
    }

    public Context getContext() {
        return mContext;
    }

    List<IRender> getRenders() {
        return mRenders;
    }

}
