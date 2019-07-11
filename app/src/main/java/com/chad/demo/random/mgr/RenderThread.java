package com.chad.demo.random.mgr;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

import com.chad.demo.random.model.Robot;
import com.chad.demo.random.render.IRender;
import com.chad.demo.random.render.Render;
import com.chad.demo.random.util.Logger;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-10.
 */
public class RenderThread implements Runnable {

    private static final String TAG = RenderThread.class.getSimpleName();

    private static final int MAX_FPS = 60;
    private static final int MIN_FPS = 12;

    private static byte[] sLock = new byte[0];

    private RenderManager mRenderManager;

    private volatile boolean mRunning = true;

    private Thread mThread;

    private Robot mRobot;
    private IRender mRender;

    private int mFPS = 30;

    private long mTimeInteval = 1000 / mFPS;

    private long mClock = 0;

    public RenderThread(RenderManager manager) {
        mRenderManager = manager;
        mRobot = new Robot();
        mRender = new Render(mRobot);
    }

    public void setCanvasSize(int width, int height) {
        if (mRender != null) {
            mRender.setCanvasSize(width, height);
        }
    }

    @Override
    public void run() {

        Logger.e(TAG, "startRenderThread");

        mClock = System.currentTimeMillis();

        drawCommon();

        while (mRunning) {
            if (mRenderManager.getSurfaceHolder() == null) {
                Logger.e(TAG, "get null surface holder");
                continue;
            }
            SurfaceHolder holder = mRenderManager.getSurfaceHolder();
            Canvas canvas = holder.lockCanvas();
            if (canvas == null) {
                Logger.e(TAG, "get null canvas. holder:%s", holder.toString());
                continue;
            }
            long start = 0, end = 0;
            start = System.currentTimeMillis();
            try {
                drawBackground(canvas);
                mRender.render(canvas, mClock);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            finally {
                holder.unlockCanvasAndPost(canvas);
            }
            end = System.currentTimeMillis();
            int cost = (int) (end - start);
//            Logger.e(TAG, "render cost : %d ms", cost);
            long sleep = mTimeInteval - cost;
            if (sleep > 0) {
                synchronized (sLock) {
                    try {
                        sLock.wait(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Logger.e(TAG, "hit fps miss");
            }
        }
    }

    public void startRenderThread() {
        mRunning = true;
        if (mThread == null || !mThread.isAlive()) {
            mThread = new Thread(this);
            mThread.start();
        }
    }

    public void stopRenderThread() {
        mRunning = false;
        synchronized (sLock) {
            sLock.notifyAll();
        }
    }

    public void setFPS(int fps) {
        if (fps < MIN_FPS) {
            fps = MIN_FPS;
        }
        if (fps > MAX_FPS) {
            fps = MAX_FPS;
        }
        mFPS = fps;
        mClock = 1000 / fps;
    }

    private void drawCommon() {
        SurfaceHolder holder = mRenderManager.getSurfaceHolder();
        if (holder == null) {
            return;
        }
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }
        try {
            drawBackground(canvas);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            holder.unlockCanvasAndPost(canvas);
        }

    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
    }
}
