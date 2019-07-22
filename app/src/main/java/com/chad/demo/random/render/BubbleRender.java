package com.chad.demo.random.render;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-22.
 */
public class BubbleRender extends BaseRender {

    private static final String TAG = BubbleRender.class.getSimpleName();

    private float A;
    private float w;//omega
    private float b;
    private float t;//fai
    private float T;

    private Matrix mMatrix;
    private Paint mPaint;

    public BubbleRender() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
    }

    @Override
    public void setCanvasSize(int width, int height) {
        super.setCanvasSize(width, height);
        A = 2 * mRobot.getSize().right;
        b = 3 * height / 4f;
        T = 2 * width;
        t = T / 4f;
        w = (float) (2 * Math.PI / T);
    }

    @Override
    public void render(Canvas canvas, long time) {
        super.render(canvas, time);
        mPosition.x = mRobot.getSpeed() * time / 1000f;
        if (mPosition.x >= mWidth) {
            mPosition.x = mPosition.x % mWidth;
        }
        mPosition.y = (float) (A * Math.sin(w * mPosition.x + t) + b);

//        Logger.d(TAG, "pos[%.2f, %.2f], t:%d", mPosition.x, mPosition.y, time);

        RectF size = mRobot.getSize();

        if (mMatrix == null) {
            mMatrix = new Matrix();
        }

        mMatrix.reset();
        Bitmap texture = mRobot.getTexture();
        float scale = size.right / (float) texture.getWidth();
        mMatrix.postScale(scale, scale);
        mMatrix.postTranslate(mPosition.x - size.right / 2f, mPosition.y - size.bottom / 2f);
        canvas.drawBitmap(mRobot.getTexture(), mMatrix, mPaint);
    }
}
