package com.thoughtworks.androidtrain.data.source.local.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sender")
public class SenderEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "nick")
    public String nick;

    @ColumnInfo(name = "avatar")
    public String avatar;
}
