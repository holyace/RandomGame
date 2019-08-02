package com.chad.demo.random.event;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-08-02.
 */
public class TouchEventWrapper implements View.OnTouchListener {

    private GestureDetector mGestureDetector;

    public TouchEventWrapper(Context context, EventManager manager) {
        mGestureDetector = new GestureDetector(context, manager);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
