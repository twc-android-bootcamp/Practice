package com.thoughtworks.androidtrain.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.thoughtworks.androidtrain.data.source.DataSource;
import com.thoughtworks.androidtrain.data.source.Repository;

public final class Dependency {
    @SuppressLint("StaticFieldLeak")
    private static volatile Dependency instance = null;

    private final Context context;

    private final DataSource dataSource;

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
        this.dataSource = new Repository(context);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
