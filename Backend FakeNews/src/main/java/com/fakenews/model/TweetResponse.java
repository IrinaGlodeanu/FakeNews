package com.fakenews.model;

public class TweetResponse {

    private String tweetId;
    private Double status;
    private Integer numberOfRelatedNews;
    private Integer numberOfRelatedBigPublications;

    public TweetResponse() {

    }

    public TweetResponse(String tweetId, Double status, Integer numberOfRelatedNews, Integer numberOfRelatedBigPublications) {
        this.tweetId = tweetId;
        this.status = status;
        this.numberOfRelatedNews = numberOfRelatedNews;
        this.numberOfRelatedBigPublications= numberOfRelatedBigPublications;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public Double getStatus() {
        return status;
    }

    public void setStatus(Double status) {
        this.status = status;
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
