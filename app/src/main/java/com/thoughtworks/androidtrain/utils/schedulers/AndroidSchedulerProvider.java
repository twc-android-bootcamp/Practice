package com.thoughtworks.androidtrain.utils.schedulers;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AndroidSchedulerProvider implements SchedulerProvider {
    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NotNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NotNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
