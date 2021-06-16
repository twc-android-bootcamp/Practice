package com.thoughtworks.androidtrain.uiflow.thread;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.R;

import java.util.Locale;

public class ThreadActivity extends AppCompatActivity {
    private static final long SECOND_DURATION = 1000L;

    private Button btnCount;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        initUI();
    }

    private void initUI() {
        btnCount = findViewById(R.id.btn_count);
        btnCount.setOnClickListener(v -> {
            startCount();
            btnCount.setEnabled(false);
        });
    }

    private void startCount() {
        new Thread(() -> {
            int count = 0;

            while (count < 10) {
                SystemClock.sleep(SECOND_DURATION);
                count++;
                int finalCount = count;
                runOnUiThread(() -> btnCount.setText(String.format(Locale.getDefault(), "%d", finalCount)));
            }

            runOnUiThread(() -> btnCount.setEnabled(true));
        }).start();
    }
}
