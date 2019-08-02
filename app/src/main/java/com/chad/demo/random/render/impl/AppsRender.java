package com.chad.demo.random.render.impl;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.chad.demo.random.constant.Constant;
import com.chad.demo.random.event.IEventHandler;
import com.chad.demo.random.mgr.RenderManager;
import com.chad.demo.random.constant.EventType;
import com.chad.demo.random.model.app.AppInfo;
import com.chad.demo.random.model.app.AppLoader;
import com.chad.demo.random.render.BaseRender;
import com.chad.demo.random.util.DisplayUtil;
import com.chad.demo.random.util.Logger;
import com.chad.demo.random.util.RenderUtil;
import com.chad.demo.random.util.ScaleType;

import java.util.List;
import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-08-01.
 */
public class AppsRender extends BaseRender implements
        AppLoader.IAppLoaderCallback, IEventHandler {

    private static final String TAG = AppsRender.class.getSimpleName();

    private AppLoader mAppLoader;
    
    private volatile boolean mLoaded;
    
    private List<AppInfo> mApps;
    
    private int mColumnCount = 5;
    private int mRowCount = -1;

    private float mColumnPadding;
    private float mRowPadding;

    private float mPaddingLeft;
    private float mPaddingRight;
    private float mPaddingTop;
    private float mPaddingBottom;

    private float mIconWidth;
    private float mTitleFontSize;
    private int mTitleColor;
    private float mTitleHeight;
    private float mItemPadding;

    private Paint mIconPaint;
    private Paint mTextPaint;

    private Rect mTextRect;

    private int mPageCount;
    private volatile int mPageNo;

    private float mOffsetX;
    private float mOffsetY;

    public AppsRender(RenderManager manager) {
        super(manager);
    }

    @Override
    protected void initRender() {
        super.initRender();
        mAppLoader = AppLoader.getInstance();

        mIconWidth = DisplayUtil.dp2px(mContext, 50);
        mItemPadding = DisplayUtil.dp2px(mContext, 5);

        mPaddingLeft = mPaddingRight = DisplayUtil.dp2px(mContext, 15);
        mPaddingTop = DisplayUtil.dp2px(mContext, 15);
        mPaddingBottom = DisplayUtil.dp2px(mContext, 100);

        mRowPadding = DisplayUtil.dp2px(mContext, 15);

        mTitleFontSize = DisplayUtil.sp2px(mContext, 10);

        mTitleColor = 0xff333333;

        mIconPaint = new Paint();
        mIconPaint.setAntiAlias(true);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTitleFontSize);
        mTextPaint.setColor(mTitleColor);
        mTextPaint.setAntiAlias(true);
//        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mTextRect = new Rect();
        mTextPaint.getTextBounds("abc", 0, 3, mTextRect);
        mTitleHeight = mTextRect.height();

        startLoadApps();
    }

    @Override
    public void setCanvasSize(int width, int height) {
        super.setCanvasSize(width, height);

        mOffsetX = 0;
        mOffsetY = 0;

        calculateSize();
    }

    private void calculateSize() {
        mColumnPadding = (mWidth - mPaddingLeft - mPaddingRight
                - mColumnCount * mIconWidth) / (float)(mColumnCount - 1);

        if (mColumnPadding < 0) {
            mColumnPadding = 0;
        }

        if (mRowCount == -1) {
            mRowCount = (int) ((mHeight - mPaddingTop - mPaddingBottom + mItemPadding) /
                    (mIconWidth + mItemPadding + mTitleHeight + mItemPadding));
        }
        else {
            mRowPadding = (mHeight - mPaddingTop - mPaddingBottom -
                    mRowCount * (mIconWidth + mItemPadding + mTitleHeight)) / (float) (mRowCount - 1);
        }

        Logger.d(Constant.MODULE, TAG, "calculate w:%d, h:%d, col:%d, row:%d\n\tpl:%.1f, pr:%.1f, pt:%.1f, pb:%.1f\n\ticon width:%.1f, item padding:%.1f, text height:%.1f",
                mWidth, mHeight, mColumnCount, mRowCount,
                mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom,
                mIconWidth, mItemPadding, mTitleHeight);
    }
    
    @Override
    public void render(Canvas canvas, long time) {
        super.render(canvas, time);
        
        if (!mLoaded || mApps == null || mApps.size() == 0) {
            return;
        }

        int size = mApps.size();

        int start = mPageNo * mColumnCount * mRowCount;
        int end = (mPageNo + 1) * mColumnCount * mRowCount;

        canvas.translate(-mOffsetX, -mOffsetY);

        for (int i = start; i < Math.min(end, size); i++) {
            AppInfo app = mApps.get(i);
            int row = (i - start) / mColumnCount;
            int col = (i - start) % mColumnCount;

            renderApp(canvas, app, col, row);
        }
        
    }

    private void renderApp(Canvas canvas, AppInfo app,
                           int colIndex, int rowIndex) {

        float x = mPaddingLeft + colIndex * mIconWidth +
                colIndex * mColumnPadding;
        float y = mPaddingTop +
                rowIndex * (mIconWidth + mItemPadding + mTitleHeight) +
                rowIndex * mRowPadding;

        RenderUtil.renderDrawable(canvas, app.getAppLogo(),
                x, y, mIconWidth, mIconWidth);

        RenderUtil.renderText(canvas, app.getAppName(), mTextPaint,
                x, y + mIconWidth + mItemPadding + mTitleHeight,
                mIconWidth, mIconWidth,
                ScaleType.CENTER);
    }
    
    private void startLoadApps() {
        mAppLoader.load(mRenderManager.getContext(), this);
    }
    
    @Override
    public void onLoadFinish(List<AppInfo> apps) {

        Logger.d(Constant.MODULE, TAG, "load app finish: %d", apps.size());

        if (apps != null && apps.size() > 0) {
            int size = apps.size();
            mPageCount = (int) Math.ceil(size / (double) (mColumnCount * mRowCount));
            mPageNo = 0;
            Logger.d(Constant.MODULE, TAG, "page count: %d", mPageCount);
        }

        mApps = apps;
        mLoaded = true;
    }

    @Override
    public boolean handleEvent(EventType type, float x, float y, float dx, float dy, float vx, float vy) {
        switch (type) {
            case TYPE_CLICK:
                return handleClick(x, y);

            case TYPE_FLING:
                return handleFling(x, y, dx, dy);

            case TYPE_FLING_END:
                return handleFlingEnd(x, y, vx, vy);
        }
        return false;
    }

    private boolean handleClick(float x, float y) {
        if (x < mPaddingLeft || x > mWidth - mPaddingRight ||
                y < mPaddingTop || y > mHeight - mPaddingBottom) {
            Logger.d(Constant.MODULE, TAG, "click on invalid area");
            return false;
        }

        float iw = mIconWidth + mColumnPadding;
        float ih = mIconWidth + mItemPadding + mTitleHeight + mRowPadding;
        float aw = x - mPaddingLeft + mColumnPadding;
        float ah = y - mPaddingTop + mRowPadding;
        int col = (int) (aw / iw);
        float ox = aw % iw;

        int row = (int) (ah / ih);
        float oy = ah % ih;

        if (ox > mIconWidth || oy > (ih - mRowPadding)) {
            Logger.d(Constant.MODULE, TAG, "clock on padding area");
            return false;
        }

        AppInfo app = findApp(col, row);
        if (app == null) {
            Logger.w(Constant.MODULE, TAG,
                    "click can't find app info, page:%d, col:%d, row:%d",
                    mPageNo, col, row);
            return false;
        }

        startApp(app);

        return false;
    }

    private boolean handleFling(float x, float y, float dx, float dy) {
        mOffsetX += dx;
        mOffsetY += dy;
        return true;
    }

    private boolean handleFlingEnd(float x, float y, float vx, float vy) {
        mOffsetX = mPageNo * mWidth;
        mOffsetY = mPageNo * mHeight;
        return false;
    }

    private AppInfo findApp(int col, int row) {
        if (mApps == null || mApps.size() == 0) {
            return null;
        }

        int offset = mPageNo * mColumnCount * mRowCount;
        int index = offset + row * mColumnCount + col;

        synchronized (mApps) {
            if (index < 0 || index > mApps.size()) {
                return null;
            }
            return mApps.get(index);
        }
    }

    private void startApp(AppInfo app) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClassName(app.getPackageName(), app.getClassName());
            intent.setPackage(app.getPackageName());
            mContext.startActivity(intent);
        } catch (Exception e) {
            Logger.e(Constant.MODULE, TAG, "start app exception:%s", e.getMessage());
        }
    }

    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int page = mPageNo;
            page++;
            if (page >= mPageCount) {
                page = 0;
            }

            Logger.d(Constant.MODULE, TAG, "current page: %d", page);

            mPageNo = page;
        }
        return true;
    }
}
