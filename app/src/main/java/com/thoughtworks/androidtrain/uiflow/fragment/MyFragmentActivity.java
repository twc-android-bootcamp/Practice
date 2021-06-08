package com.thoughtworks.androidtrain.uiflow.fragment;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.utils.UiUtils;

public class MyFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_fragment_layout);
        initUI();
    }

    private void initUI() {
        Button btnAndroid = findViewById(R.id.btn_android);
        btnAndroid.setOnClickListener(v -> UiUtils.replaceFragmentAndAddToBackStack(getSupportFragmentManager(),
                new ContentAndroidFragment(),
                R.id.content,
                null));

        Button btnJava = findViewById(R.id.btn_java);
        btnJava.setOnClickListener(v -> UiUtils.replaceFragmentAndAddToBackStack(getSupportFragmentManager(),
                new ContentJavaFragment(),
                R.id.content,
                null));

        UiUtils.replaceFragmentAndAddToBackStack(getSupportFragmentManager(),
                new ContentAndroidFragment(),
                R.id.content,
                null);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
