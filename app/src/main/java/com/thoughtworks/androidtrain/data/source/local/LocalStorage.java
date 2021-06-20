package com.thoughtworks.androidtrain.data.source.local;

import com.thoughtworks.androidtrain.data.model.Tweet;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface LocalStorage {
    boolean isKnown();

    void setKnown(boolean isKnown);

    List<Tweet> getTweetsFromRaw();

    Single<Boolean> updateTweets(List<Tweet> tweets);

    Flowable<List<Tweet>> getTweets();
}
