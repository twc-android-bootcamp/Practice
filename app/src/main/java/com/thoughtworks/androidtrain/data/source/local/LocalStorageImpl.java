package com.thoughtworks.androidtrain.data.source.local;

import android.content.Context;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.data.model.Comment;
import com.thoughtworks.androidtrain.data.model.Image;
import com.thoughtworks.androidtrain.data.model.Sender;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.local.room.AppDatabase;
import com.thoughtworks.androidtrain.data.source.local.room.model.CommentEntity;
import com.thoughtworks.androidtrain.data.source.local.room.model.ImageEntity;
import com.thoughtworks.androidtrain.data.source.local.room.model.SenderEntity;
import com.thoughtworks.androidtrain.data.source.local.room.model.TweetEntity;
import com.thoughtworks.androidtrain.definitions.Constants;
import com.thoughtworks.androidtrain.utils.FileUtils;
import com.thoughtworks.androidtrain.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class LocalStorageImpl implements LocalStorage {
    private static final String KEY_KNOWN = "known";

    private final Context context;

    private final Gson gson = new Gson();

    private final AppDatabase appDatabase;

    public LocalStorageImpl(Context context) {
        this.context = context;
        this.appDatabase = Room.databaseBuilder(context, AppDatabase.class, "practice-db").build();
    }

    @Override
    public boolean isKnown() {
        return SharedPreferenceUtils.readBoolean(context, Constants.SHARED_PREFERENCE_FILE, KEY_KNOWN, false);
    }

    @Override
    public void setKnown(boolean isKnown) {
        SharedPreferenceUtils.writeBoolean(context, Constants.SHARED_PREFERENCE_FILE, KEY_KNOWN, isKnown);
    }

    @Override
    public List<Tweet> getTweetsFromRaw() {
        List<Tweet> tweets = gson.fromJson(FileUtils.getStringFromRaw(context, R.raw.tweets), new TypeToken<List<Tweet>>() {
        }.getType());

        return tweets.stream().filter(tweet -> tweet.getError() == null && tweet.getUnknownError() == null).collect(Collectors.toList());
    }

    @Override
    public Single<Boolean> updateTweets(List<Tweet> tweets) {
        return Single.create(emitter -> {
            try {
                appDatabase.clearAllTables();

                appDatabase.runInTransaction(() -> {
                    tweets.forEach(tweet -> {
                        TweetEntity tweetEntity = toRoomTweet(tweet);
                        tweetEntity.senderId = insertRoomSender(tweet.getSender());
                        long tweetId = appDatabase.tweetDao().insert(tweetEntity).blockingGet();

                        if (tweet.getImages() != null) {
                            tweet.getImages().forEach(image -> {
                                ImageEntity imageEntity = toRoomImage(image, tweetId);
                                appDatabase.imageDao().insert(imageEntity).blockingGet();
                            });
                        }

                        if (tweet.getComments() != null) {
                            tweet.getComments().forEach(comment -> {
                                CommentEntity commentEntity = toRoomComment(comment, tweetId, insertRoomSender(comment.getSender()));
                                appDatabase.commentDao().insert(commentEntity).blockingGet();
                            });
                        }
                    });
                });
            } catch (Throwable t) {
                emitter.onError(t);
                return;
            }

            emitter.onSuccess(true);
        });
    }

    @Override
    public Flowable<List<Tweet>> getTweets() {
        return appDatabase.tweetDao().getAll()
                .map(tweetEntities -> {
                    List<SenderEntity> senderEntities = appDatabase.senderDao().getAll().blockingFirst();
                    List<ImageEntity> imageEntities = appDatabase.imageDao().getAll().blockingFirst();
                    List<CommentEntity> commentEntities = appDatabase.commentDao().getAll().blockingFirst();

                    List<Tweet> tweets = new ArrayList<>();
                    for (TweetEntity tweetEntity : tweetEntities) {
                        Tweet tweet = toTweet(tweetEntity);
                        senderEntities.stream().filter(senderEntity1 -> senderEntity1.id == tweetEntity.senderId).map(this::toSender).findFirst().ifPresent(tweet::setSender);
                        tweet.setImages(imageEntities.stream().filter(imageEntity -> imageEntity.id == tweetEntity.id).map(imageEntity -> new Image(imageEntity.url)).collect(Collectors.toList()));
                        tweet.setComments(commentEntities.stream().filter(commentEntity -> commentEntity.tweetId == tweetEntity.id).map(commentEntity -> new Comment(commentEntity.content, senderEntities.stream().filter(senderEntity -> senderEntity.id == commentEntity.senderId).map(this::toSender).findFirst().orElse(null))).collect(Collectors.toList()));
                        tweets.add(tweet);
                    }

                    return tweets;
                });
    }

    private Tweet toTweet(TweetEntity tweetEntity) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetEntity.content);

        return tweet;
    }

    private Sender toSender(SenderEntity senderEntity) {
        Sender sender = new Sender();
        sender.setUsername(senderEntity.username);
        sender.setNick(senderEntity.nick);
        sender.setAvatar(senderEntity.avatar);

        return sender;
    }

    private TweetEntity toRoomTweet(Tweet tweet) {
        TweetEntity tweetEntity = new TweetEntity();
        tweetEntity.id = 0;
        tweetEntity.content = tweet.getContent();

        return tweetEntity;
    }

    private long insertRoomSender(Sender sender) {
        SenderEntity senderEntity = toRoomSender(sender);
        return appDatabase.senderDao().insert(senderEntity).blockingGet();
    }

    private SenderEntity toRoomSender(Sender sender) {
        SenderEntity senderEntity = new SenderEntity();
        senderEntity.id = 0;
        senderEntity.username = sender.getUsername();
        senderEntity.nick = sender.getNick();
        senderEntity.avatar = sender.getAvatar();

        return senderEntity;
    }

    private ImageEntity toRoomImage(Image image, long tweetId) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.id = 0;
        imageEntity.tweetId = tweetId;
        imageEntity.url = image.getUrl();

        return imageEntity;
    }

    private CommentEntity toRoomComment(Comment comment, long tweetId, long senderId) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.id = 0;
        commentEntity.tweetId = tweetId;
        commentEntity.senderId = senderId;
        commentEntity.content = comment.getContent();

        return commentEntity;
    }
}
