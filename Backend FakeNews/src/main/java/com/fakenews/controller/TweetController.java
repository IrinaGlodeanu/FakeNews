package com.fakenews.controller;

import com.fakenews.entities.Tweet;
import com.fakenews.repository.TweetRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @GetMapping
    public ResponseEntity<List<Tweet>> getTweet() {
        List<Tweet> result = Lists.newArrayList(tweetRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addTweet(@RequestBody Tweet tweet) {
        tweetRepository.save(tweet);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
