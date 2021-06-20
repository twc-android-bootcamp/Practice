package com.thoughtworks.androidtrain.uiflow.recyclerview;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.DataSource;
import com.thoughtworks.androidtrain.functors.Action1;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetsViewModel extends ViewModel {
    private DataSource dataSource;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<Tweet>> tweetList = new MutableLiveData<>(new ArrayList<>());

    public void setDependencies(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void fetchTweets(@NonNull Action1<Throwable> errorHandler) {
        if (dataSource == null) {
            return;
        }

        Disposable subscribe = dataSource
                .fetchTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
