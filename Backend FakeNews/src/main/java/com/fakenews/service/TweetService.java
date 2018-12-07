package com.fakenews.service;

import com.fakenews.entities.Tweet;
import com.fakenews.repository.TweetRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public List<Tweet> getFakeTweets() {
        return Lists.newArrayList(tweetRepository.findAll())
                .stream()
                .filter(Tweet::isFake)
                .collect(Collectors.toList());
    }

    public List<Tweet> getTrueTweets() {
        return Lists.newArrayList(tweetRepository.findAll())
                .stream()
                .filter(Tweet::isTrue)
                .collect(Collectors.toList());
    }

    public List<Tweet> findAll() {
        return Lists.newArrayList(tweetRepository.findAll());
    }

    public void saveTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    public void deleteAll() {
        tweetRepository.deleteAll();
    }

    public Optional<Tweet> findById(String id){
        return tweetRepository.findById(id);
    }

}
