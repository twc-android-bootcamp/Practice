package com.thoughtworks.androidtrain.uiflow.recyclerview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.PracticeApp;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.utils.Dependency;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecyclerViewActivity extends AppCompatActivity {

    private TweetAdapter tweetAdapter;

    private Dependency dependency;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void initialize() {
        dependency = ((PracticeApp) getApplication()).getDependency();
        initUI();
        initData();
    }

    private void initData() {
        Disposable subscribe = dependency.getDataSource()
                .fetchTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tweets -> tweetAdapter.setData(tweets),
                        throwable -> Toast.makeText(RecyclerViewActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
        compositeDisposable.add(subscribe);
    }

    private void initUI() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new TweetAdapter(this);
        recyclerView.setAdapter(tweetAdapter);
    }
}
