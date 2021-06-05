package com.thoughtworks.androidtrain.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UiUtils {
    public static void replaceFragmentAndAddToBackStack(@NonNull FragmentManager fragmentManager,
                                                        @NonNull Fragment fragment, int frameId, @Nullable String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }
}
