package com.thoughtworks.androidtrain.data.source;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.definitions.Constants;
import com.thoughtworks.androidtrain.utils.FileUtils;
import com.thoughtworks.androidtrain.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class LocalStorageImpl implements LocalStorage {
    private static final String KEY_KNOWN = "known";

    private final Context context;

    private Gson gson = new Gson();

    public LocalStorageImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isKnown() {
        return SharedPreferenceUtils.readBoolean(context, Constants.SHARED_PREFERENCE_FILE, KEY_KNOWN, false);
    }

    @Override
    public void setKnown(boolean isKnown) {
        SharedPreferenceUtils.writeBoolean(context, Constants.SHARED_PREFERENCE_FILE, KEY_KNOWN, isKnown);
    }

    @Override
    public List<Tweet> getTweets() {
        List<Tweet> tweets = gson.fromJson(FileUtils.getStringFromRaw(context, R.raw.tweets), new TypeToken<List<Tweet>>() {
        }.getType());

        List<Tweet> filteredTweets = new ArrayList<>();
        for (Tweet tweet : tweets
        ) {
            if (tweet.getError() != null || tweet.getUnknownError() != null) {
                continue;
            }

            filteredTweets.add(tweet);
        }

        return filteredTweets;
    }
}
