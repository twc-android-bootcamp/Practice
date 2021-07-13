package com.thoughtworks.androidtrain.uiflow.recyclerview;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.DataSource;
import com.thoughtworks.androidtrain.utils.schedulers.SchedulerProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.mockito.Mockito.when;


public class TweetsViewModelUnitTest {

    private DataSource dataSource;

    private SchedulerProvider schedulerProvider;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void before() {
        dataSource = Mockito.mock(DataSource.class);
        schedulerProvider = Mockito.mock(SchedulerProvider.class);
    }

    @Test
    public void test_fetch_tweets() {
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet());
        tweets.add(new Tweet());

        when(dataSource.fetchTweets()).thenReturn(Flowable.just(tweets));
        when(schedulerProvider.io()).thenReturn(Schedulers.trampoline());
        when(schedulerProvider.ui()).thenReturn(Schedulers.trampoline());

        TweetsViewModel tweetsViewModel = new TweetsViewModel();
        tweetsViewModel.setDependencies(dataSource, schedulerProvider);
        tweetsViewModel.fetchTweets(throwable -> {
        });

        Assert.assertEquals(2, Objects.requireNonNull(tweetsViewModel.tweetList.getValue()).size());
    }
}