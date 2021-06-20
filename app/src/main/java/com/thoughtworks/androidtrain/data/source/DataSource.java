package com.thoughtworks.androidtrain.data.source;

import com.thoughtworks.androidtrain.data.model.Tweet;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface DataSource {
    boolean isKnown();

    void setKnown(boolean isKnown);

    Flowable<List<Tweet>> fetchTweets();
}
