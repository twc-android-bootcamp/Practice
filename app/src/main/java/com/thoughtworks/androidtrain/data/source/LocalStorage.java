package com.thoughtworks.androidtrain.data.source;

public interface LocalStorage {
    boolean isKnown();

    void setKnown(boolean isKnown);
}
