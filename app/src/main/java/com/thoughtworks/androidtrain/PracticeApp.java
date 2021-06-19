package com.thoughtworks.androidtrain;

import android.app.Application;

import com.thoughtworks.androidtrain.utils.Dependency;

public class PracticeApp extends Application {

    private Dependency dependency = null;

    @Override
    public void onCreate() {
        super.onCreate();
        dependency = Dependency.getInstance(this);
    }

    public Dependency getDependency() {
        return dependency;
    }
}
