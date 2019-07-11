package com.chad.demo.random.mgr;

import android.view.SurfaceView;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-06-28.
 */
public class Presenter {

    private SurfaceView mSurfaceView;
    private RenderManager mRenderManager;

    public Presenter() {

    }

    public void init(SurfaceView view) {
        mSurfaceView = view;
        if (mRenderManager == null) {
            mRenderManager = new RenderManager();
        }
        mRenderManager.init(mSurfaceView);
    }

    public RenderManager getRenderManager() {
        return mRenderManager;
    }

    public void startRender() {
        if (mRenderManager != null) {
            mRenderManager.startRender();
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
