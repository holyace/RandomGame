package com.chad.demo.random.model;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-06-28.
 */
public interface IRobot {

    PointF getCurrentPosition();

    void setCurrentPosition(PointF point);

    RectF getSize();

    void setSize(RectF rect);

    Bitmap getTexture();

    void setTexture(Bitmap bitmap);
}
