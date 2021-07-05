package com.chad.demo.random.controller;

import android.content.Context;
import android.view.SurfaceView;

import com.chad.demo.random.R;
import com.chad.demo.random.constant.EventType;
import com.chad.demo.random.event.EventManager;
import com.chad.demo.random.event.IEventHandler;
import com.chad.demo.random.mgr.RenderManager;
import com.chad.demo.random.model.BubbleRobot;
import com.chad.demo.random.model.Robot;
import com.chad.demo.random.render.IRender;
import com.chad.demo.random.render.impl.AppsRender;
import com.chad.demo.random.render.impl.BubbleRender;
import com.chad.demo.random.render.impl.RandomRender;
import com.chad.demo.random.util.DisplayUtil;
import com.chad.demo.random.util.RandomUtil;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-06-28.
 */
public class Presenter {

    private SurfaceView mSurfaceView;
    private RenderManager mRenderManager;
    private Context mContext;

    private EventManager mEventManager;

    public Presenter() {
    }

    public void init(SurfaceView view) {
        mContext = view.getContext();
        mSurfaceView = view;

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setKeepScreenOn(true);

        mEventManager = new EventManager(view);

        if (mRenderManager == null) {
            mRenderManager = new RenderManager();
        }
        mRenderManager.init(mSurfaceView);
        mRenderManager.setFPS(30);

        initRenders();
    }

    private void initRenders() {
        IRender render = null;

        render = new RandomRender(null);
        mRenderManager.addRender(render);

//        render = new AppsRender(mRenderManager);
//        mRenderManager.addRender(render);
//        mEventManager.registerEventHandler(EventType.TYPE_CLICK, (IEventHandler) render);
//        mEventManager.registerEventHandler(EventType.TYPE_SCROLL, (IEventHandler) render);
//        mEventManager.registerEventHandler(EventType.TYPE_FLING, (IEventHandler) render);

        int mins = DisplayUtil.dp2px(mContext, 20);
        int maxs = DisplayUtil.dp2px(mContext, 30);

        for (int i = 0; i < 10; i++) {
            BubbleRobot robot = new BubbleRobot(RandomUtil.getRandom().nextBoolean() ?
                    R.drawable.bubble : R.drawable.bubble2,
                    (int) (RandomUtil.getRandom().nextFloat() * (maxs - mins) + mins));
            robot.loadDrawable(mContext.getResources());

            render = new BubbleRender(mRenderManager);
            render.setRobot(robot);
            mRenderManager.addRender(render);
        }

    }

    public RenderManager getRenderManager() {
        return mRenderManager;
    }

    public void startRender() {
        if (mRenderManager != null) {
            mRenderManager.startRender();
        }
    }

    public void onResume() {
        if (mRenderManager != null) {
            mRenderManager.startRender();
        }
    }

    public void onPause() {
        if (mRenderManager != null) {
            mRenderManager.stopRender();
        }
    }

    public void stop() {
        if (mRenderManager != null) {
            mRenderManager.stopRender();
        }
    }

    public void onDestroy() {
        if (mRenderManager != null) {
            mRenderManager.destroyRender();
        }
    }

}
