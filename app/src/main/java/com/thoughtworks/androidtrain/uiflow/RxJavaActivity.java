package com.thoughtworks.androidtrain.uiflow;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.R;

import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    private static final long ONE_SECOND = 1000L;

    private Button btnCount;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void initUI() {
        btnCount = findViewById(R.id.btn_count);
        btnCount.setOnClickListener(v -> {
            startCount();
            btnCount.setEnabled(false);
        });
    }

    private void startCount() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            int i = 0;
            while (i < 10) {
                SystemClock.sleep(ONE_SECOND);
                emitter.onNext(++i);
            }

            emitter.onComplete();

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        btnCount.setText(String.format(Locale.getDefault(), "%d", integer));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(RxJavaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        btnCount.setEnabled(true);
                    }
                });
    }
}
