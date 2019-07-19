package com.chad.demo.random.render;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.chad.demo.random.mgr.MovePolicy;
import com.chad.demo.random.model.IDrawModel;
import com.chad.demo.random.model.MoveModel;
import com.chad.demo.random.model.Robot;
import com.chad.demo.random.util.Direction;

import java.util.Random;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-06-28.
 */
public class Render implements IRender {

    private static final String TAG = Render.class.getSimpleName();

    private Paint mPaint;
    private Paint mBgPaint;

    private float mBaseLine;

    private PointF mLastPoint;
    private PointF mCurrentPoint;

    private Direction mLastDirection;

    private float mLastX, mLastY, mX, mY, mR;

    private float mSpeed = 10;

    private Path mPath;

    private Random mRandom;

    private Direction mDirection = Direction.GO_RIGHT;

    private int mWidth;
    private int mHeigth;

    private int mCount;

    private IDrawModel mDrawModel;

    private MovePolicy mPolicy;

    private MoveModel mMoveModel;

    private long mLastTime;

    public Render(Robot robo) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(Color.parseColor("#000000"));
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeWidth(1);

        mR = 5;

        mPath = new Path();

        mRandom = new Random();

        mPolicy = new MovePolicy();

        mMoveModel = new MoveModel();

        mMoveModel.setSpeed(mSpeed);
    }


    @Override
    public void setDrawModel(IDrawModel model) {
        mDrawModel = model;
    }

    @Override
    public void setCanvasSize(int width, int height) {
        mWidth = width;
        mHeigth = height;

        mLastX = mX = mWidth / 2f;
        mLastY = mY = mHeigth / 2f;

        mPath.moveTo(mX, mY);

        mMoveModel.setRange(width, height);

        mMoveModel.setPosition(width / 2f, height / 2f, Direction.GO_RIGHT);
    }

    @Override
    public void render(Canvas canvas, long time) {

        mPolicy.move(mWidth, mHeigth, mMoveModel);

        drawMove(canvas, mMoveModel);

    }

    private void drawMove(Canvas canvas, MoveModel moveModel) {
        canvas.drawPath(moveModel.getPath(), mBgPaint);
        PointF point = moveModel.getNow().getPosition();
        canvas.drawCircle(point.x, point.y, mR, mPaint);
    }

}
