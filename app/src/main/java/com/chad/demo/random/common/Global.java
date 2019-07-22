package com.chad.demo.random.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-22.
 */
public class Global {

    private static Application sApp;
    private static Activity sTopAct;

    public static Application getApp() {
        return sApp;
    }

    static void setApp(Application app) {
        if (app != null) {
            app.registerActivityLifecycleCallbacks(sLifecycle);
        }
        else if (sApp != null) {
            sApp.unregisterActivityLifecycleCallbacks(sLifecycle);
        }
        sApp = app;
    }

    public static Activity getTopActivity() {
        return sTopAct;
    }

    public static boolean isTopActivity(Activity act) {
        return sTopAct == act;
    }

    private static Application.ActivityLifecycleCallbacks sLifecycle = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            sTopAct = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity == sTopAct) {
                sTopAct = null;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}
