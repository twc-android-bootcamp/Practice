package com.thoughtworks.androidtrain.uiflow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.PracticeApp;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.utils.Dependency;

public class SpActivity extends AppCompatActivity {

    private Dependency dependency = null;

    private TextView tvInfo;

    private Button btnGotIt;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        initialize();
    }

    private void initialize() {
        dependency = ((PracticeApp) getApplication()).getDependency();

        tvInfo = findViewById(R.id.tv_info);
        btnGotIt = findViewById(R.id.btn_got_it);

        btnGotIt.setOnClickListener(v -> {
            dependency.getLocalStorage().setKnown(true);
            refreshStatus();
        });

        refreshStatus();
    }

    private void refreshStatus() {
        tvInfo.setText(dependency.getLocalStorage().isKnown() ? R.string.welcome_back : R.string.sp_tips);
        btnGotIt.setVisibility(dependency.getLocalStorage().isKnown() ? View.GONE : View.VISIBLE);
    }
}
