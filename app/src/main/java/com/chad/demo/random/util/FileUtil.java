package com.chad.demo.random.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    public static Bitmap readAssetsBitmap(Context context, String name) throws IOException {

        try (InputStream is = context.getAssets().open(name)) {
            return BitmapFactory.decodeStream(is);
        }

    }

    public static String readAssetsString(Context context, String name) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(name)))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap squareBitmap(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        int targetWidth = Math.min(width, height);
        return Bitmap.createBitmap(src, (width - targetWidth) / 2, (height - targetWidth) / 2, targetWidth, targetWidth);
    }
}
