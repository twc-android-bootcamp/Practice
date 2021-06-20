package com.thoughtworks.androidtrain.data.source.remote;

import com.thoughtworks.androidtrain.data.model.Tweet;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface RemoteDataSource {
    Single<List<Tweet>> fetchTweets();
}
