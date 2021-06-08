package com.thoughtworks.androidtrain.uiflow.recyclerview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.definitions.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private TweetAdapter tweetAdapter;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        initUI();
        initData();
    }

    private void initData() {
        tweetAdapter.setData(getTweets());
    }

    private List<Tweet> getTweets() {
        List<Tweet> tweets = gson.fromJson(Constants.TWEETS, new TypeToken<List<Tweet>>() {
        }.getType());

        List<Tweet> filteredTweets = new ArrayList<>();
        for (Tweet tweet : tweets
        ) {
            if (tweet.getError() != null || tweet.getUnknownError() != null) {
                continue;
            }

            filteredTweets.add(tweet);
        }

        return filteredTweets;
    }

    private void initUI() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new TweetAdapter(this);
        recyclerView.setAdapter(tweetAdapter);
    }
}
