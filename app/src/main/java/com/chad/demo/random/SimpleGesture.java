package com.chad.demo.random;

import android.view.MotionEvent;
import android.view.View;

public class SimpleGesture implements View.OnTouchListener {

    private float mDownX;
    private float mDownY;

    public SimpleGesture() {

    }

    @Override
    public boolean onTouch(final View v, MotionEvent event) {

        int actionMask = event.getActionMasked();

        float x = event.getX();
        float y = event.getY();

        switch (actionMask) {

            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                final float dx = x - mDownX;
                final float dy = y - mDownY;

                v.post(new Runnable() {
                    @Override
                    public void run() {
                        v.setRotationX((float) (Math.PI * dx / (float) v.getWidth()));
                        v.setRotationY((float) (Math.PI * dy / (float) v.getHeight()));
                    }
                });

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:

                break;

        }

        return true;
    }
}
