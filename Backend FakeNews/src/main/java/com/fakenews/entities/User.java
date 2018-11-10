package com.fakenews.entities;

import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RedisHash("User")
public class User {

    private String id;
    private String username;
    private LocalDateTime registrationDate;
    private String email;
    private List<Tweet> tweets;
    private Double trustDegree;

    public boolean isFake() {
        return this.trustDegree < 0.5;
    }

    public boolean isTrue() {
        return !isFake();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
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

    public Double getTrustDegree() {
        return trustDegree;
    }

    public void setTrustDegree(Double trustDegree) {
        this.trustDegree = trustDegree;
    }
}
