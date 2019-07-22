package com.chad.demo.random.mgr;

import android.content.Context;
import android.view.SurfaceView;

import com.chad.demo.random.R;
import com.chad.demo.random.common.Global;
import com.chad.demo.random.model.BubbleRobot;
import com.chad.demo.random.model.Robot;
import com.chad.demo.random.render.BubbleRender;
import com.chad.demo.random.render.IRender;
import com.chad.demo.random.util.DisplayUtil;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-06-28.
 */
public class Presenter {

    private SurfaceView mSurfaceView;
    private RenderManager mRenderManager;
    private Context mContext;

    public Presenter() {

    }

    public void init(SurfaceView view) {
        mContext = view.getContext();
        mSurfaceView = view;
        if (mRenderManager == null) {
            mRenderManager = new RenderManager();
        }
        mRenderManager.init(mSurfaceView);

        initRenders();
    }

    private void initRenders() {
        IRender render = null;
//        render = new RandomRender(null);
//        mRenderManager.addRender(render);

        Robot robot = null;
//        robot = new BubbleRobot(null);
//        ((BubbleRobot) robot).loadDrawable(mContext.getResources());
//        render = new BubbleRender();
//        render.setRobot(robot);
//        mRenderManager.addRender(render);

        robot = new BubbleRobot(R.drawable.bubble2,
                DisplayUtil.dp2px(mContext, 25));
        ((BubbleRobot) robot).loadDrawable(mContext.getResources());
        render = new BubbleRender();
        render.setRobot(robot);
        mRenderManager.addRender(render);
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
