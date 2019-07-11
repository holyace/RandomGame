package com.chad.demo.random.render;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.chad.demo.random.mgr.MovePolicy;
import com.chad.demo.random.model.IDrawModel;
import com.chad.demo.random.model.MoveModel;
import com.chad.demo.random.model.PositionModel;
import com.chad.demo.random.model.Robot;
import com.chad.demo.random.util.Direction;
import com.chad.demo.random.util.Turn;

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

        mMoveModel.setPosition(width / 2f, height / 2f, Direction.GO_RIGHT);
    }

    @Override
    public void render(Canvas canvas, long time) {
//        int baseline = height / 2;
//        mX += 1;
//        mY = (float) (150 * Math.sin(mX * 2 * Math.PI / 180)) + baseline;
//        if (mX >= width) {
//            mX = 0;
//        }
//        canvas.drawLine(0, baseline, width, baseline, mBgPaint);
//        canvas.drawLine(width / 2f, 0, width / 2f, height, mBgPaint);
//        canvas.drawLine(mX, mY, mX + 2, mY + 2, mPaint);
//        canvas.drawCircle(mX, mY, mR, mPaint);
        next();
        canvas.drawPath(mPath, mBgPaint);
//        canvas.drawLine(mLastX, mX, mLastY, mY, mBgPaint);
        canvas.drawCircle(mX, mY, mR, mPaint);

        mPolicy.move(mWidth, mHeigth, mMoveModel);

        drawMove(canvas, mMoveModel);

    }

    private void drawMove(Canvas canvas, MoveModel moveModel) {
        canvas.drawPath(moveModel.getPath(), mBgPaint);
        PointF point = moveModel.getNow().getPosition();
        canvas.drawCircle(point.x, point.y, mR, mPaint);
    }

    private void next() {
        int w = mWidth;
        int h = mHeigth;
        Direction direction = nextDirection();
        float x = mX, y = mY;
        switch (direction) {
            case GO_UP:
                y -= mSpeed;
                if (y < 0) {
                    y = 0;
                }
                break;

            case GO_RIGHT:
                x += mSpeed;
                if (x > w) {
                    x = w;
                }
                break;

            case GO_BOTTOM:
                y += mSpeed;
                if (y > h) {
                    y = h;
                }
                break;

            case GO_LEFT:
                x -= mSpeed;
                if (x < 0) {
                    x = 0;
                }
                break;

            default:
                //
        }
//        mPath.lineTo(x, y);
//        mPath.quadTo((x + mX) / 2f, (y + mY) / 2f, x, y);
        mPath.quadTo((x + mLastX) / 2f, (y + mLastY) / 2f, x, y);
        mLastX = mX;
        mLastY = mY;

        mX = x;
        mY = y;

        mCount++;

//        if (mCount >= 1000) {
//            mCount = 0;
//            mPath.reset();
//            mPath.moveTo(x, y);
//        }
    }

    /**
     * 0:up, 1:right, 2:down, 3:left
     *
     * 40% go ahead, 25% turn left, 25% turn right, 10% turn back
     * @return
     */
    private Direction nextDirection() {
        int random = mRandom.nextInt(100);
        if (random < 80) {
            //Keep
        }
        else if (random < 87) {
            mDirection = mDirection.turn(Turn.TURN_LEFT);
        }
        else if (random < 95) {
            mDirection = mDirection.turn(Turn.TURN_RIGHT);
        }
        else {
            mDirection = mDirection.turn(Turn.TURN_BACK);
        }
        return mDirection;
    }
}
