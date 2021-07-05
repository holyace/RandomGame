package com.chad.demo.random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chad.demo.random.util.DisplayUtil;
import com.chad.demo.random.util.FileUtil;

import java.io.IOException;

public class PhotoActivity extends AppCompatActivity implements
        SurfaceHolder.Callback,
        Handler.Callback {

    private static final String TAG = "PhotoActivity";

    private static final int RENDER = 1;

    private SurfaceView mSurfaceView;

    private int mWidth;
    private int mHeight;

    private HandlerThread mHandlerThread;
    private Handler mWorkerHandler;

    private Bitmap mImage;

    private Matrix mMatrix;

    private volatile float mScale = 0.25f;

    private Paint mPaint;
    private Paint mRulerPaint;
    private Paint mRulerTextPaint;

    private final int mRulerPaddingX = 0;
    private final int mRulerPaddingY = 0;

    private final int mRulerTextPadding = 10;

    private final int mShortRulerLength = 10;
    private final int mLongRulerLength = 20;

    private float mPaintDy;

    private Rect mTextRect = new Rect();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSurfaceView = new SurfaceView(this);
        setContentView(mSurfaceView);

        mSurfaceView.getHolder().addCallback(this);
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {

            float mDownX;
            float mDownY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int actionMask = event.getActionMasked();

                float x = event.getX();
                float y = event.getY();

                switch (actionMask) {

                    case MotionEvent.ACTION_DOWN:
                        mDownX = x;
                        mDownY = y;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        final float dx = x - mDownX;
                        final float dy = y - mDownY;

                        mScale += (float) (dx / (float) v.getWidth());
                        if (mScale <= 0.01f) {
                            mScale = 0.01f;
                        }
//                        mRationY += (float) (Math.PI * dy / (float) v.getHeight());

                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    default:

                        break;

                }

                return true;
            }
        });
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);

        mRulerPaint = new Paint();
        mRulerPaint.setColor(Color.YELLOW);
        mRulerPaint.setAntiAlias(true);

        mRulerTextPaint = new Paint();
        mRulerTextPaint.setColor(Color.YELLOW);
        mRulerTextPaint.setAntiAlias(true);
        mRulerTextPaint.setTextSize(DisplayUtil.sp2px(this, 6));
        mRulerTextPaint.setTextAlign(Paint.Align.LEFT);

        Paint.FontMetrics metrics = mRulerTextPaint.getFontMetrics();
        mPaintDy = (metrics.bottom - metrics.top) / 2f - metrics.bottom;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mHandlerThread == null || !mHandlerThread.isAlive()) {
            mHandlerThread = new HandlerThread("render-thread");
            mHandlerThread.start();
            mWorkerHandler = new Handler(mHandlerThread.getLooper(), this);
        }
        mWorkerHandler.obtainMessage(RENDER).sendToTarget();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWidth = width;
        mHeight = height;

        updateMatrix();
    }

    private void updateMatrix() {
        if (mMatrix == null) {
            mMatrix = new Matrix();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopRender();
    }

    private void stopRender() {
        if (mWorkerHandler != null) {
            mWorkerHandler.removeCallbacksAndMessages(null);
        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        mHandlerThread = null;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {

        switch (msg.what) {
            case RENDER:
                renderFrame();
                break;

        }

        return true;
    }

    private void renderFrame() {
        if (mSurfaceView == null) {
            return;
        }
        SurfaceHolder holder = mSurfaceView.getHolder();
        Canvas canvas = holder.lockCanvas();
        try {

            canvas.drawColor(0x000000, PorterDuff.Mode.CLEAR);

            drawRuler(canvas, mWidth, mHeight);

            drawContent(canvas, mWidth, mHeight);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }

        mWorkerHandler.sendEmptyMessageDelayed(RENDER, 30);
    }

    private void drawRuler(Canvas canvas, int width, int height) {
        canvas.drawLine(mRulerPaddingX, mRulerPaddingY, width, mRulerPaddingY, mRulerPaint);

        for (int i = mRulerPaddingX; i < width; i++) {
            if (i > mRulerPaddingX && (i - mRulerPaddingX) % 50 == 0) {
                float y = mRulerPaddingY + mLongRulerLength;
                canvas.drawLine(i, mRulerPaddingY, i, y, mRulerPaint);

                String text = String.valueOf(i);
                mRulerTextPaint.getTextBounds(text, 0, text.length(), mTextRect);
                canvas.drawText(text, i - mTextRect.right / 2f,
                        y + mRulerTextPadding + mTextRect.bottom + mPaintDy,
                        mRulerTextPaint);
            }
            else if ((i - mRulerPaddingX) % 10 == 0) {
                canvas.drawLine(i, mRulerPaddingY, i, mRulerPaddingY + mShortRulerLength, mRulerPaint);
            }
        }

        canvas.drawLine(mRulerPaddingX, mRulerPaddingY, mRulerPaddingX, height, mRulerPaint);

        for (int i = mRulerPaddingY; i < height; i++) {
            if (i > mRulerPaddingY && (i - mRulerPaddingY) % 50 == 0) {

                float x = mRulerPaddingX + mLongRulerLength;
                canvas.drawLine(mRulerPaddingX, i, x, i, mRulerPaint);

                String text = String.valueOf(i);
                mRulerTextPaint.getTextBounds(text, 0, text.length(), mTextRect);

                canvas.drawText(text, x + mRulerTextPadding,
                        i + mTextRect.bottom + mPaintDy,
                        mRulerTextPaint);

            }
            else if ((i - mRulerPaddingX) % 10 == 0) {
                canvas.drawLine(mRulerPaddingX, i, mRulerPaddingX + mShortRulerLength, i, mRulerPaint);
            }
        }
    }

    private void drawContent(Canvas canvas, int width, int height) {
        if (mImage == null) {
            loadImage();
        }

        int centerX = width / 2;
        int centerY = height / 2;

        int targetSize = (int) (width * mScale);
        float scale = targetSize / (float) mImage.getWidth();

        float startX = centerX - targetSize / 2f;
        float startY = centerY - targetSize / 2f;

        mMatrix.reset();
        mMatrix.postScale(scale, scale);
        mMatrix.postTranslate(startX, startY);
        canvas.drawBitmap(mImage, mMatrix, mPaint);

        mMatrix.reset();
        mMatrix.postScale(scale, 0.5f * scale);
        mMatrix.postSkew(-1f, 0f);
        mMatrix.postTranslate(startX + 0.5f * targetSize, startY - 0.5f * targetSize);
        canvas.drawBitmap(mImage, mMatrix, mPaint);

        mMatrix.reset();
        mMatrix.postRotate(90f);
        mMatrix.postScale(0.5f * scale, scale);
        mMatrix.postSkew(0, -1f);
        mMatrix.postTranslate(startX + 1.5f * targetSize, startY - 0.5f * targetSize);
        canvas.drawBitmap(mImage, mMatrix, mPaint);
    }

    private void loadImage() {
        try {
            mImage = FileUtil.squareBitmap(FileUtil.readAssets(this.getApplication(), "image_2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }
}
