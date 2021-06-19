package com.thoughtworks.androidtrain.uiflow.recyclerview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.PracticeApp;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.utils.Dependency;

public class RecyclerViewActivity extends AppCompatActivity {

    private TweetAdapter tweetAdapter;

    private Dependency dependency;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        initialize();
    }

    private void initialize() {
        dependency = ((PracticeApp) getApplication()).getDependency();
        initUI();
        initData();
    }

    private void initData() {
        tweetAdapter.setData(dependency.getLocalStorage().getTweets());
    }

    private void initUI() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new TweetAdapter(this);
        recyclerView.setAdapter(tweetAdapter);
    }
}
