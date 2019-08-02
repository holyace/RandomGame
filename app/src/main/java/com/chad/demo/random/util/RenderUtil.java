package com.chad.demo.random.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-08-01.
 */
public class RenderUtil {

    public static void renderDrawable(Canvas canvas, Drawable drawable,
                                      float x, float y,
                                      float width, float height) {
        drawable.setBounds((int) x, (int) y, (int) (x + width), (int) (y + height));
        drawable.draw(canvas);
    }

    public static void renderBitmap(Canvas canvas, Bitmap bitmap,
                                    Paint paint, float x, float y,
                                    float width, float height,
                                    ScaleType type) {
        Matrix matrix = new Matrix();
        matrix.postTranslate(x, y);
        float scale = width / x;
        matrix.postScale(scale, scale);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public static void renderText(Canvas canvas, String text,
                                  Paint paint, float x, float y,
                                  float width, float height,
                                  ScaleType type) {

        float tw = paint.measureText(text);
        int end = text.length();
        if (tw < width) {
            x += (width - tw) / 2f;
        }
        else {
            end = paint.breakText(text, 0, end, true, width, null);
        }

        canvas.drawText(text, 0, end, x, y, paint);
    }
}
