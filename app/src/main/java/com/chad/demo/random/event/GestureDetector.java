package com.chad.demo.random.event;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.chad.demo.random.constant.Constant;
import com.chad.demo.random.util.Logger;

public class GestureDetector implements View.OnTouchListener {

    private static final String TAG = GestureDetector.class.getSimpleName();

    private static final long LONG_PRESS_TIME = 500;

    private Context mContext;

    private OnGestureListener mListener;

    private float mLastX;
    private float mLastY;
    private long mLastTapTime;
    private long mLastDownTime;

    private boolean mInScroll;

    private boolean mScrollHandled;
    private boolean mLongPressHandled;

    private boolean mInTap;

    private int mTouchSlop;
    private int mDoubleTapTimeout;
    private int mDoubleTapSlop;

    public GestureDetector(Context context, OnGestureListener listener) {
        mListener = listener;
        mContext = context;

        ViewConfiguration viewConfig = ViewConfiguration.get(mContext);

        mTouchSlop = viewConfig.getScaledTouchSlop();
        mDoubleTapSlop = viewConfig.getScaledDoubleTapSlop();
        mDoubleTapTimeout = ViewConfiguration.getDoubleTapTimeout();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                if (handleDown(event)) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (handleMove(event)) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (handleUp(event)) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                if (handleCancel(event)) {
                    return true;
                }
                break;
        }

        return false;
    }

    private boolean handleDown(MotionEvent event) {
        if (mListener.onDown(event)) {
            return true;
        }

        long time = System.currentTimeMillis();

//        Logger.e(Constant.MODULE, TAG, "handleDown last:%d, dt:%d", mLastTime, time - mLastTime);

        //in press
        if (mLastDownTime > 0 && time - mLastDownTime >= LONG_PRESS_TIME){

            mLongPressHandled = mListener.onLongPress(event);

            mLastDownTime = -1;
            clear();

            if (mLongPressHandled) {
                return true;
            }
            return true;
        }
        else {

            mLastX = event.getX();
            mLastY = event.getY();
            mLastDownTime = time;
        }

        return false;
    }

    private boolean handleMove(MotionEvent event) {

        long time = System.currentTimeMillis();
        float x = event.getX();
        float y = event.getY();

        float distanceX = x - mLastX;
        float distanceY = y - mLastY;

        //scroll
        if (mInScroll || Math.abs(distanceX) >= mTouchSlop || Math.abs(distanceY) >= mTouchSlop) {
            if (mScrollHandled = mListener.onScroll(event, distanceX, distanceY)) {
                mInScroll = false;
                return true;
            }
            else {
                mLastX = x;
                mLastY = y;
                mInScroll = true;
            }
        }
        else {
            if (handleDown(event)) {
                return true;
            }
        }
        return false;
    }

    private boolean handleUp(MotionEvent event) {

        long time = System.currentTimeMillis();

        float x = event.getX();
        float y = event.getY();

        if (mInScroll && !mScrollHandled && mListener.onUp(event)) {
            return true;
        }

        if (mInTap && (time - mLastTapTime) <= mDoubleTapTimeout) {
            mListener.onDoubleClick(event);
            return true;
        }
        else {
            mListener.onClick(event);
        }

        //TODO
        return false;
    }

    private boolean handleCancel(MotionEvent event) {
        //TODO
        return handleUp(event);
    }

    private void clear() {

    }

    public interface OnGestureListener {

        boolean onDown(MotionEvent event);

        boolean onLongPress(MotionEvent event);

        void onClick(MotionEvent event);

        void onDoubleClick(MotionEvent event);

        boolean onScroll(MotionEvent event, float distanceX, float distanceY);

        boolean onFling(MotionEvent event, float velocityX, float velocityY);

        boolean onUp(MotionEvent event);
    }
}
