package com.thoughtworks.androidtrain.data.source.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.androidtrain.data.model.Tweet;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemoteDataSourceImpl implements RemoteDataSource {

    private static final String TWEETS_URL = "https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith/tweets";

    private final OkHttpClient client = new OkHttpClient();

    private final Gson gson = new Gson();

    @Override
    public Single<List<Tweet>> fetchTweets() {
        return Single.create(emitter -> {
            Request request = new Request.Builder()
                    .url(TWEETS_URL)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                List<Tweet> tweets = gson.fromJson(Objects.requireNonNull(response.body()).string(), new TypeToken<List<Tweet>>() {
                }.getType());

                emitter.onSuccess(tweets);
            } catch (Throwable e) {
                emitter.onError(e);
            }
        });
    }
}
