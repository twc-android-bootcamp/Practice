package com.thoughtworks.androidtrain.utils.schedulers;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Scheduler;

public interface SchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
