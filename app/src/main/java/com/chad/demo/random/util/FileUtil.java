package com.chad.demo.random.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static Bitmap readAssets(Context context, String name) throws IOException {

        try (InputStream is = context.getAssets().open(name)) {
            return BitmapFactory.decodeStream(is);
        }

    }

    public static Bitmap squareBitmap(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        int targetWidth = Math.min(width, height);
        return Bitmap.createBitmap(src, (width - targetWidth) / 2, (height - targetWidth) / 2, targetWidth, targetWidth);
    }
}
