package com.fakenews.entities;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Tweet")
public class Tweet {

    private String id;
    private String description;
    private User user;
    private boolean isFake;
    private List<Tweet> tweets;

    public Tweet(String id, String description, boolean isFake, User user) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.isFake = isFake;
        tweets = new ArrayList<Tweet>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFake() {
        return isFake;
    }

    public void setFake(boolean fake) {
        isFake = fake;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}