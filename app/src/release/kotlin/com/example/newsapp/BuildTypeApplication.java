package com.example.newsapp;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class BuildTypeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.Tree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable throwable) {
                if (priority >= Log.INFO && Fabric.isInitialized()) {
                    Crashlytics.log(priority, tag, message);
                    if (throwable != null) {
                        Crashlytics.logException(throwable);
                    }
                }
            }
        });
    }
}