package com.fakenews.app.adapter;

import com.fakenews.entities.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetAdapter {

    private String twitterAPI;

    public String getTwitterAPI() {
        return twitterAPI;
    }

    public void setTwitterAPI(String twitterAPI) {
        this.twitterAPI = twitterAPI;
    }

    public List<Tweet> getTweets() {
        // We will use the API to get tweets and convert them to out entities
        List<Tweet> result = new ArrayList<>();
        return result;
    }
}
