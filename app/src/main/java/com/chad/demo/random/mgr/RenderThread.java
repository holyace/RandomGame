package com.chad.demo.random.mgr;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.chad.demo.random.R;
import com.chad.demo.random.render.IRender;
import com.chad.demo.random.util.Logger;

import java.util.List;

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

//    private Robot mRobot;
//    private IRender mRender;

    private int mFPS = 60;

    private long mTimeInteval = 1000 / mFPS;

    private long mClock = -1;

    public RenderThread(RenderManager manager) {
        mRenderManager = manager;
//        mRobot = new Robot();
//        mRender = new RandomRender(mRobot);
    }

    public void setCanvasSize(int width, int height) {
//        if (mRender != null) {
//            mRender.setCanvasSize(width, height);
//        }
        List<IRender> renders = mRenderManager.getRenders();
        if (renders != null && renders.size() > 0) {
            for(IRender render : renders) {
                render.setCanvasSize(width, height);
            }
        }
        initBg();
    }

    @SuppressLint("NewApi")
    @Override
    public void run() {

        Logger.e(TAG, "startRenderThread");

        mClock = System.currentTimeMillis();
//        drawCommon();

        while (mRunning) {

            long time = System.currentTimeMillis();

            if (mRenderManager.getSurfaceHolder() == null) {
                Logger.e(TAG, "get null surface holder");
                continue;
            }
            SurfaceHolder holder = mRenderManager.getSurfaceHolder();
            Canvas canvas = holder.lockHardwareCanvas();
            if (canvas == null) {
                Logger.e(TAG, "get null canvas. holder:%s", holder.toString());
                continue;
            }
            long start;
            long end;

            long end1 = 0;
            start = System.currentTimeMillis();
            try {
                drawBackground(canvas);

                end1 = System.currentTimeMillis();

//                mRender.render(canvas, mClock);
                List<IRender> renders = mRenderManager.getRenders();
                if (renders != null && renders.size() > 0) {
                    for (IRender render : renders) {
                        render.render(canvas, time - mClock);
                    }
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            finally {
                holder.unlockCanvasAndPost(canvas);
            }
            end = System.currentTimeMillis();
            int cost = (int) (end - start);
//            Logger.d(TAG, "render bg cost %d ms", (end1 - start));
//            Logger.d(TAG, "render cost : %d ms", cost);
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
                Logger.e(TAG, "hit fps miss, f:%dhz, t:%dms, cost %dms",
                        mFPS, mTimeInteval, cost);
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

    private Bitmap mBg;
    private Matrix mMatrix;
    private Paint mPaint;

    private void drawBackground(Canvas canvas) {
        if (mBg == null) {
            initBg();
        }
        canvas.drawBitmap(mBg, mMatrix, mPaint);
    }

    private void initBg() {
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inPreferredConfig = Bitmap.Config.RGB_565;
        mBg = BitmapFactory.decodeResource(mRenderManager.getContext().getResources(),
                R.drawable.bg, ops);
        int w = mRenderManager.getWidth();
        int h = mRenderManager.getHeight();

        int bw = mBg.getWidth();
        int bh = mBg.getHeight();

        Logger.d(TAG, "bitmap[%d, %d], surface[%d, %d]", bw, bh, w, h);

        mMatrix = new Matrix();

        float scale = 1f;
        float translateX = 0;
        float translateY = 0;
        if (bw < w || bh < h) {
            float sx = w / (float)bw;
            float sy = h / (float)bh;

            if (sx > sy) {
                scale = sx;
                translateY = (scale * bh - h) / 2f;
            }
            else {
                scale = sy;
                translateX = (scale * bw - w) / 2f;
            }
        }
        else {
            float sx = w / (float)bw;
            float sy = h / (float)bh;

            if (sx > sy) {
                scale = sx;
                translateY = (bh * scale - h) / 2f;
            }
            else {
                scale = sy;
                translateX = (bw * scale - w) / 2f;
            }
        }

        mMatrix.postScale(scale, scale);
        mMatrix.postTranslate(translateX, translateY);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
