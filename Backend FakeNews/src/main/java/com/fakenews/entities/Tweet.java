package com.fakenews.entities;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Tweet")
public class Tweet {

    private String id;
    private Double trustDegree;
    private Integer numberOfRelatedNews;
    private Integer numberOfRelatedBigPublications;

    public Tweet(){

    }

    public Tweet(String tweetId, Double status, Integer numberOfRelatedNews, Integer numberOfRelatedBigPublications) {
        this.id = tweetId;
        this.trustDegree = status;
        this.numberOfRelatedNews = numberOfRelatedNews;
        this.numberOfRelatedBigPublications= numberOfRelatedBigPublications;
    }

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

    public void setTrustDegree(Double trustDegree) {
        this.trustDegree = trustDegree;
    }

    public Double getTrustDegree() {
        return trustDegree;
    }

    public Integer getNumberOfRelatedNews() {
        return numberOfRelatedNews;
    }

    public void setNumberOfRelatedNews(Integer numberOfRelatedNews) {
        this.numberOfRelatedNews = numberOfRelatedNews;
    }

    public Integer getNumberOfRelatedBigPublications() {
        return numberOfRelatedBigPublications;
    }

    public void setNumberOfRelatedBigPublications(Integer numberOfRelatedBigPublications) {
        this.numberOfRelatedBigPublications = numberOfRelatedBigPublications;
    }
}
