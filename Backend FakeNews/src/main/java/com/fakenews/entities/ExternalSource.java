package com.fakenews.entities;

import org.springframework.data.redis.core.RedisHash;

import java.util.Date;
import java.util.List;

@RedisHash("ExternalSource")
public class ExternalSource {

    private String url;
    private Date creationDate;
    private List<Concept> concepts;
    private Double trustFactor;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public Double getTrustFactor() {
        return trustFactor;
    }

    public void setTrustFactor(Double trustFactor) {
        this.trustFactor = trustFactor;
    }
}
