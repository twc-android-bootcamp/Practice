package com.thoughtworks.androidtrain.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.thoughtworks.androidtrain.data.source.LocalStorage;
import com.thoughtworks.androidtrain.data.source.LocalStorageImpl;

public final class Dependency {
    @SuppressLint("StaticFieldLeak")
    private static volatile Dependency instance = null;

    private Context context;

    private final LocalStorage localStorage;

    public static Dependency getInstance(Context context) {
        if (instance == null) {
            synchronized (Dependency.class) {
                if (instance == null) {
                    instance = new Dependency(context);
                }
            }
        }
        return instance;
    }

    private Dependency(Context context) {
        this.context = context;
        localStorage = new LocalStorageImpl(context);
    }

    public LocalStorage getLocalStorage() {
        return localStorage;
    }
}
