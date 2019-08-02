package com.chad.demo.random.event;

import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.chad.demo.random.constant.Constant;
import com.chad.demo.random.constant.EventType;
import com.chad.demo.random.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-08-02.
 */
public class EventManager implements GestureDetector.OnGestureListener {

    private static final String TAG = EventManager.class.getSimpleName();

    private static volatile EventManager sInstance;

    private SparseArray<List<IEventHandler>> mEventHandlers = new SparseArray<>();

    private byte[] mLock = new byte[0];

    private EventManager() {

    }

    public static EventManager getInstance() {
        if (sInstance == null) {
            synchronized (EventManager.class) {
                if (sInstance == null) {
                    sInstance = new EventManager();
                }
            }
        }
        return sInstance;
    }

    private boolean dispatchEvent(EventType type, float x, float y, float dx, float dy, float vx, float vy) {
        synchronized (mLock) {
            List<IEventHandler> list = mEventHandlers.get(type.ordinal());
            if (list != null) {
                for (IEventHandler handler : list) {
                    if (handler.handleEvent(type, x, y, dx, dy, vx, vy)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void unregisterEventHandler(IEventHandler handler) {
        synchronized (mLock) {
            for (int i = 0; i < mEventHandlers.size(); i++) {
                List<IEventHandler> list = mEventHandlers.valueAt(i);
                if (list != null) {
                    list.remove(handler);
                }
            }
        }
    }

    public void registerEventHandler(EventType type, IEventHandler handler) {
        synchronized (mLock) {
            List<IEventHandler> list = mEventHandlers.get(type.ordinal());
            if (list == null) {
                list = new ArrayList<>();
                list.add(handler);
                mEventHandlers.put(type.ordinal(), list);
            }
            else {
                if (!list.contains(handler)) {
                    list.add(handler);
                }
            }
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        Logger.d(Constant.MODULE, TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Logger.d(Constant.MODULE, TAG, "onSingleTapUp x:%f, y:%f", e.getX(), e.getY());
        return dispatchEvent(EventType.TYPE_CLICK, e.getX(), e.getY(), 0, 0, 0, 0);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Logger.d(Constant.MODULE, TAG, "onScroll x:%f, y:%f", distanceX, distanceY);
        return dispatchEvent(EventType.TYPE_FLING, e2.getX(), e2.getY(), distanceX, distanceY, 0, 0);
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        Logger.d(Constant.MODULE, TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Logger.d(Constant.MODULE, TAG, "onFling x:%f, y:%f", velocityX, velocityY);
        return dispatchEvent(EventType.TYPE_FLING_END, e2.getX(), e2.getY(), 0, 0, velocityX, velocityY);
    }
}
