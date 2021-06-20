package com.thoughtworks.androidtrain.uiflow.recyclerview;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.DataSource;
import com.thoughtworks.androidtrain.functors.Action1;
import com.thoughtworks.androidtrain.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class TweetsViewModel extends ViewModel {
    private DataSource dataSource;

    private SchedulerProvider schedulerProvider;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<Tweet>> tweetList = new MutableLiveData<>(new ArrayList<>());

    public void setDependencies(@NonNull DataSource dataSource, @NonNull SchedulerProvider schedulerProvider) {
        this.dataSource = dataSource;
        this.schedulerProvider = schedulerProvider;
    }

    public void fetchTweets(@NonNull Action1<Throwable> errorHandler) {
        Disposable subscribe = dataSource
                .fetchTweets()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        tweets -> tweetList.postValue(tweets),
                        errorHandler::invoke);
        compositeDisposable.add(subscribe);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
