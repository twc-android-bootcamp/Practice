package com.thoughtworks.androidtrain.data.source;

import android.content.Context;

import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.local.LocalStorage;
import com.thoughtworks.androidtrain.data.source.local.LocalStorageImpl;
import com.thoughtworks.androidtrain.data.source.remote.RemoteDataSource;
import com.thoughtworks.androidtrain.data.source.remote.RemoteDataSourceImpl;
import com.thoughtworks.androidtrain.utils.schedulers.SchedulerProvider;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;

public class Repository implements DataSource {

    private final LocalStorage localStorage;

    private final RemoteDataSource remoteDataSource;

    private final SchedulerProvider schedulerProvider;

    public Repository(Context context, SchedulerProvider schedulerProvider) {
        this.schedulerProvider = schedulerProvider;
        this.localStorage = new LocalStorageImpl(context);
        this.remoteDataSource = new RemoteDataSourceImpl();
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
        remoteDataSource.fetchTweets()
                .subscribeOn(schedulerProvider.io())
                .subscribe(tweets -> {
                    List<Tweet> filteredTweets = tweets.stream().filter(tweet -> tweet.getError() == null && tweet.getUnknownError() == null).collect(Collectors.toList());
                    localStorage.updateTweets(filteredTweets).subscribeOn(schedulerProvider.io()).subscribe();
                }, throwable -> {
                });

        return localStorage.getTweets();
    }
}
