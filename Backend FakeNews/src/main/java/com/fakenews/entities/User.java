package com.fakenews.entities;

import org.springframework.data.redis.core.RedisHash;

import java.util.Date;
import java.util.List;

@RedisHash("User")
public class User {

    private String username;
    private Date registrationDate;
    private String email;
    private List<Tweet> tweets;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}
