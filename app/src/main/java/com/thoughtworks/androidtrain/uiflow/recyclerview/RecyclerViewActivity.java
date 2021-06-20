package com.thoughtworks.androidtrain.uiflow.recyclerview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
        initViewModel();
        initUI();
    }

    private void initViewModel() {
        TweetsViewModel tweetsViewModel = new ViewModelProvider(this).get(TweetsViewModel.class);
        tweetsViewModel.setDependencies(dependency.getDataSource());
        tweetsViewModel.tweetList.observe(this, tweets -> tweetAdapter.setData(tweets));

        tweetsViewModel.fetchTweets(throwable -> Toast.makeText(RecyclerViewActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void initUI() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new TweetAdapter(this);
        recyclerView.setAdapter(tweetAdapter);
    }
}
