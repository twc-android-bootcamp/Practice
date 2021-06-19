package com.thoughtworks.androidtrain.data.source;

import com.thoughtworks.androidtrain.data.model.Tweet;

import java.util.List;

public interface LocalStorage {
    boolean isKnown();

    void setKnown(boolean isKnown);

    List<Tweet> getTweets();
}
