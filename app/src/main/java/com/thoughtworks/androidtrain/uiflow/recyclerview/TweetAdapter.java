package com.thoughtworks.androidtrain.uiflow.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.androidtrain.R;
import com.thoughtworks.androidtrain.data.model.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter {

    private static final String TAG = "TweetAdapter";

    private static final int VIEW_TYPE_TWEET = 0;
    private static final int VIEW_TYPE_BOTTOM = 1;

    private final Context context;

    private List<Tweet> tweets = new ArrayList<>();

    public TweetAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Tweet> tweets) {
        this.tweets.clear();
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_TWEET) {
            return new TweetViewHolder(inflater.inflate(R.layout.item_tweet, parent, false));
        } else {
            return new BottomViewHolder(inflater.inflate(R.layout.item_bottom, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == VIEW_TYPE_TWEET) {
            Tweet tweet = tweets.get(position);
            TweetViewHolder tweetViewHolder = (TweetViewHolder) holder;

            tweetViewHolder.tvName.setText("");
            tweetViewHolder.tvContent.setText("");

            if (tweet.getSender() == null) {
                return;
            }

            tweetViewHolder.tvName.setText(tweet.getSender().getNick());
            tweetViewHolder.tvContent.setText(tweet.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return this.tweets.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < tweets.size()) {
            return VIEW_TYPE_TWEET;
        }

        return VIEW_TYPE_BOTTOM;
    }

    public static class TweetViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imAvatar;
        private final TextView tvName;
        private final TextView tvContent;

        public TweetViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imAvatar = itemView.findViewById(R.id.avatar);
            tvName = itemView.findViewById(R.id.name);
            tvContent = itemView.findViewById(R.id.content);
        }
    }

    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
