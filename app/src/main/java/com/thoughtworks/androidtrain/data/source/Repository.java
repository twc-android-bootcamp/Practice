package com.thoughtworks.androidtrain.data.source;

import android.content.Context;

import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.local.LocalStorage;
import com.thoughtworks.androidtrain.data.source.local.LocalStorageImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository implements DataSource {

    private final LocalStorage localStorage;

    public Repository(Context context) {
        localStorage = new LocalStorageImpl(context);
    }

    @Override
    public boolean isKnown() {
        return localStorage.isKnown();
    }

    @Override
    public void setKnown(boolean isKnown) {
        localStorage.setKnown(isKnown);
    }

    @Override
    public Flowable<List<Tweet>> fetchTweets() {
        localStorage.updateTweets(localStorage.getTweetsFromRaw())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> {
                }, throwable -> {
                });

        return localStorage.getTweets();
    }
}
