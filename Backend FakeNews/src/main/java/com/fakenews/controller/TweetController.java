package com.fakenews.controller;

import com.fakenews.entities.Tweet;
import com.fakenews.repository.TweetRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {

    @Autowired
    private TweetRepository repository;

    @GetMapping
    public ResponseEntity<List<Tweet>> getTweet() {
        List<Tweet> result = Lists.newArrayList(repository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addTweet(@RequestBody Tweet tweet) {
        repository.save(tweet);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Tweet>> getTweetById(String id) {
        Optional<Tweet> result = repository.findById(id);
        List<Optional<Tweet>> list = Lists.newArrayList(result);
        System.out.println("here has begun in FindTweetById");
        Iterator<Optional<Tweet>> iterator = list.iterator();
//        if (iterator.hasNext()){
//            iterator.next();
//        }
        iterator.next();
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
