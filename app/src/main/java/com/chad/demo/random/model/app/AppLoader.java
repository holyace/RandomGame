package com.chad.demo.random.model.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;

import com.chad.demo.random.util.SafeRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-08-01.
 */
public class AppLoader {

    private HandlerThread mThread;
    private Handler mHandler;

    private static volatile AppLoader sInstance;

    private AppLoader() {
        mThread = new HandlerThread("app-loader");
        mThread.start();

        mHandler = new Handler(mThread.getLooper());
    }

    public static AppLoader getInstance() {
        if (sInstance == null) {
            synchronized (AppLoader.class) {
                if (sInstance == null) {
                    sInstance = new AppLoader();
                }
            }
        }
        return sInstance;
    }

    public void load(Context context, IAppLoaderCallback callback) {
        mHandler.post(new LoadTask(context, callback));
    }

    public interface IAppLoaderCallback {

        void onLoadFinish(List<AppInfo> apps);
    }

    private static class LoadTask extends SafeRunnable {

        private IAppLoaderCallback mCallback;
        private Context mContext;

        public LoadTask(Context context, IAppLoaderCallback callback) {
            mContext = context;
            mCallback = callback;
        }

        @Override
        public void safeRun() {
            PackageManager pm = mContext.getPackageManager();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> packages = pm.queryIntentActivities(intent, 0);

            if (packages == null || packages.size() == 0) {
                mCallback.onLoadFinish(null);
            }
            else {
                List<AppInfo> apps = new ArrayList<>(packages.size());
                for (ResolveInfo ri : packages) {
                    AppInfo ai = new AppInfo();

                    Drawable drawable = ri.loadIcon(pm);
                    if (drawable == null) {
                        continue;
                    }

                    ai.setPackageName(ri.activityInfo.packageName);
                    ai.setClassName(ri.activityInfo.name);
                    ai.setAppName((String) ri.loadLabel(pm));
                    ai.setAppLogo(drawable);

                    apps.add(ai);
                }
                mCallback.onLoadFinish(apps);
            }
        }
    }
}
