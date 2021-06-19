package com.thoughtworks.androidtrain.utils;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.RawRes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static String getStringFromRaw(Context context, @RawRes int id) {
        String str;
        try {
            Resources r = context.getResources();
            InputStream is = r.openRawResource(id);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                os.write(i);
                i = is.read();
            }

            str = os.toString();
            is.close();
        } catch (IOException e) {
            str = "";
        }

        return str;
    }
}
