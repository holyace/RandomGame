package com.chad.demo.random.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.chad.demo.random.R;
import com.chad.demo.random.common.Global;
import com.chad.demo.random.util.DisplayUtil;
import com.chad.demo.random.util.RandomUtil;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-22.
 */
public class BubbleRobot extends Robot {

    private RectF mSize;
    private RectF mPosition;
    private Bitmap mTexture;

    private int mResId = R.drawable.bubble;

    private int mSpped;

    public BubbleRobot(IDrawModel model) {
        this(R.drawable.bubble,
                DisplayUtil.dp2px(Global.getApp(), 25));
    }

    public BubbleRobot(int id, int size) {
        mSize = new RectF(0, 0, size, size);
        mResId = id;
        mPosition = new RectF();

        int mins = 50, maxs = 70;
        mSpped = (int) (RandomUtil.getRandom().nextFloat() * (maxs - mins) + mins);
    }

    public void loadDrawable(Resources res) {
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inPreferredConfig = Bitmap.Config.ARGB_4444;
        mTexture = BitmapFactory.decodeResource(res, mResId, ops);
    }

    @Override
    public Bitmap getTexture() {
        return mTexture;
    }

    @Override
    public RectF getSize() {
        return mSize;
    }

    @Override
    public int getSpeed() {
        return mSpped;
    }
}
