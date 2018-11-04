package com.fakenews.entities;

import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;

@RedisHash("Tweet")
public class Tweet {

    private String id;
    private User author;
    private LocalDateTime creationDate;
    private String text;
    private List<Concept> subjects;
    private String targetPerson;
    private List<String> keywords;
    private List<ExternalSource> externalSources;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Concept> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Concept> subjects) {
        this.subjects = subjects;
    }

    public String getTargetPerson() {
        return targetPerson;
    }

    public void setTargetPerson(String targetPerson) {
        this.targetPerson = targetPerson;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<ExternalSource> getExternalSources() {
        return externalSources;
    }

    public void setExternalSources(List<ExternalSource> externalSources) {
        this.externalSources = externalSources;
    }

    public Double getTrustDegree() {
        return trustDegree;
    }

    public void setTrustDegree(Double trustDegree) {
        this.trustDegree = trustDegree;
    }

    public void printStatus() {
        System.out.print("Status");
    }
}
