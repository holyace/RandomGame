package com.chad.demo.random.util;

import android.graphics.Matrix;

public class LogUtil {

    public static String hexHash(Object obj) {
        return obj == null ? "" : String.format("%s@0x%s", obj.getClass().getSimpleName(), Integer.toHexString(obj.hashCode()));
    }

    public static void printMatrix(String module, String tag, Matrix matrix) {
        if (matrix == null) {
            Logger.w(module, tag, "null matrix");
            return;
        }

        float[] values = new float[9];
        matrix.getValues(values);
        StringBuilder sb = new StringBuilder(hexHash(matrix)).append("\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(values[i * 3 + j]).append("\t");
            }
            sb.append("\n");
        }
        Logger.i(module, tag, sb.toString());
    }
}
