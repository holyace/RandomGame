package com.chad.demo.random.render.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.chad.demo.random.constant.Constant;
import com.chad.demo.random.mgr.RenderManager;
import com.chad.demo.random.render.BaseRender;
import com.chad.demo.random.util.LogUtil;
import com.chad.demo.random.util.Logger;
import com.chad.demo.random.util.RandomUtil;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-22.
 */
public class BubbleRender extends BaseRender {

    private static final String TAG = BubbleRender.class.getSimpleName();

    //y = A*sin(w*x + t) + b

    private float A;
    private float w;//omega
    private float b;
    private float t;//fai
    private float T;
    private float dx;

    private Matrix mMatrix;
    private Paint mPaint;

    public BubbleRender(RenderManager manager) {
        super(manager);
    }

    @Override
    protected void initRender() {
        super.initRender();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
    }

    @Override
    public void setCanvasSize(int width, int height) {
        super.setCanvasSize(width, height);
        A = 1.5f * mRobot.getSize().right;
        float minb = 60 * height / 100f, maxb = 87 * height / 100f;
        b = RandomUtil.getRandom().nextFloat() * (maxb - minb) + minb;
        float minT = 1.5f * width, maxT = 2.5f * width;
        T = RandomUtil.getRandom().nextFloat() * (maxT - minT) + minT;
        float mint = 0, maxt = T / 4f;
        t = RandomUtil.getRandom().nextFloat() * (maxt - mint) + mint;
        w = (float) (2 * Math.PI / T);
        float maxdx = width, mindx = 0;
        dx = RandomUtil.getRandom().nextFloat() * (maxdx - mindx) + mindx;
    }

    private RectF mRect = new RectF();

    @Override
    public void render(Canvas canvas, long time) {
        super.render(canvas, time);
        mPosition.x = (mRobot.getSpeed() * time / 1000f - dx - mRobot.getSize().right) %
                (mWidth + mRobot.getSize().right) - mRobot.getSize().right / 2f;
        if (mPosition.x + mRobot.getSize().right < 0) {
            return;
        }
//        if (mPosition.x - 3 * mRobot.getSize().right / 4f > mWidth) {
//            return;
//        }
        mPosition.y = (float) (A * Math.sin(w * mPosition.x + t) + b);

        Logger.d(Constant.MODULE, TAG, "%s pos[%.2f, %.2f], t: %d",
                LogUtil.hexHash(mRobot), mPosition.x, mPosition.y, time);

        Rect size = mRobot.getSize();

        if (mMatrix == null) {
            mMatrix = new Matrix();
        }

        mMatrix.reset();
        Bitmap texture = mRobot.getTexture();
        float scale = size.right / (float) texture.getWidth();
        mMatrix.setScale(scale, scale);
        mMatrix.postTranslate(mPosition.x - size.right / 2f, mPosition.y - size.bottom / 2f);
        canvas.drawBitmap(mRobot.getTexture(), mMatrix, mPaint);
        mRect.left = mPosition.x - size.right / 2f;
        mRect.right = mRect.left + size.right;
        canvas.drawBitmap(mRobot.getTexture(), mRobot.getSize(), mRect, mPaint);
    }
}
