package com.fakenews.controller;

import com.fakenews.entities.Tweet;
import com.fakenews.service.TweetService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @GetMapping
    public ResponseEntity<List<Tweet>> getTweets() {
        List<Tweet> result = Lists.newArrayList(tweetService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addTweet(@RequestBody Tweet tweet) {
        tweetService.saveTweet(tweet);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweetById(String id) {
        Optional<Tweet> result = tweetService.findById(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteAll(){
        tweetService.deleteAll();
        return new ResponseEntity(HttpStatus.GONE);
    }
}
