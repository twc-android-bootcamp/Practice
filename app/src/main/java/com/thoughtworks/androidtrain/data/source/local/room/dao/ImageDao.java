package com.thoughtworks.androidtrain.data.source.local.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thoughtworks.androidtrain.data.source.local.room.model.ImageEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


@Dao
public interface ImageDao {
    @Query("SELECT * FROM image")
    Flowable<List<ImageEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(ImageEntity imageEntity);
}
