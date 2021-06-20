package com.thoughtworks.androidtrain.data.source.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thoughtworks.androidtrain.data.source.local.room.dao.CommentDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.ImageDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.SenderDao;
import com.thoughtworks.androidtrain.data.source.local.room.dao.TweetDao;
import com.thoughtworks.androidtrain.data.source.local.room.model.CommentEntity;
import com.thoughtworks.androidtrain.data.source.local.room.model.ImageEntity;
import com.thoughtworks.androidtrain.data.source.local.room.model.SenderEntity;
import com.thoughtworks.androidtrain.data.source.local.room.model.TweetEntity;

@Database(entities = {ImageEntity.class, SenderEntity.class, CommentEntity.class, TweetEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();

    public abstract SenderDao senderDao();

    public abstract CommentDao commentDao();

    public abstract TweetDao tweetDao();
}
