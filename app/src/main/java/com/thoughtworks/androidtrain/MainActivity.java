package com.thoughtworks.androidtrain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        Button btn1 = findViewById(R.id.constraint_layout);
        btn1.setOnClickListener(v -> startActivity(new Intent(this, ConstraintActivity.class)));

        Button btn2 = findViewById(R.id.login);
        btn2.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }
}