package com.thoughtworks.androidtrain.data.source.local.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "comment",
        foreignKeys = {
                @ForeignKey(entity = TweetEntity.class, parentColumns = "id", childColumns = "tweet_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = SenderEntity.class, parentColumns = "id", childColumns = "sender_id", onDelete = ForeignKey.CASCADE)
        })
public class CommentEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "tweet_id", index = true)
    public long tweetId;

    @ColumnInfo(name = "sender_id", index = true)
    public long senderId;

    @ColumnInfo(name = "content")
    public String content;
}
