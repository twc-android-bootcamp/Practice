package com.thoughtworks.androidtrain.data.source;

import android.content.Context;

import com.thoughtworks.androidtrain.definitions.Constants;
import com.thoughtworks.androidtrain.utils.SharedPreferenceUtils;

public class LocalStorageImpl implements LocalStorage {
    private static final String KEY_KNOWN = "known";

    private final Context context;

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
}
