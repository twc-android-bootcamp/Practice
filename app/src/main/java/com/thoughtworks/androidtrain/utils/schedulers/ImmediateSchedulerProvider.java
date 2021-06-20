package com.thoughtworks.androidtrain.utils.schedulers;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ImmediateSchedulerProvider implements SchedulerProvider {
    @NotNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @NotNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NotNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
