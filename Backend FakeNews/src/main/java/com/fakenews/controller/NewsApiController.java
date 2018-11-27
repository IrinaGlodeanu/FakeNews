package com.fakenews.controller;

import com.fakenews.model.NewsResult;
import com.fakenews.model.TweetResponse;
import com.fakenews.service.NLPService;
import com.fakenews.service.NewsApiService;
import com.fakenews.service.TwitterOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/newsApi")
public class NewsApiController {

    @Autowired
    private NewsApiService service;

    @Autowired
    private NLPService nlpService;

    static final List<String> whitelist = Arrays.asList("cnn", "nbc", "cbs", "nasa", "forbes", "bbc", "abc", "washington", "times", "wall street", "wsj");

    private TwitterOperationsService twitterService = new TwitterOperationsService();

    @GetMapping
    public ResponseEntity<NewsResult> searchNewsBySomeWords(@RequestParam("query") String query) {

        return new ResponseEntity<>(service.queryNews(query), HttpStatus.OK);
    }

    @GetMapping("/getRelatedNews")
    public ResponseEntity<List<NewsResult>> getRelatedNews(@RequestParam("twitterText") String twitterText) throws IOException {

        List<String> keyWords = nlpService.retrieveKeywords(twitterText);
        List<NewsResult> newsResult = new ArrayList<>();

        for (String keyword : keyWords) {
            if (Character.isUpperCase(keyword.charAt(0))) {
                newsResult.add(service.queryNews(keyword));
            }
        }
        return new ResponseEntity<>(newsResult, HttpStatus.OK);
    }

    @GetMapping("/getRelatedNewsWithStatus")
    public List<TweetResponse> getRelatedNewsWithStatus() throws TwitterException, IOException {
        List<Status> timeLineTweets =
                twitterService.getTimeLineTweets();

        List<TweetResponse> responseList = new ArrayList<>();

        for (Status tweet : timeLineTweets) {
            TweetResponse response = new TweetResponse();
            response.setTweetId(tweet.getId());
            response.setStatus(0);

            for (String whitelister : whitelist) {
                if (tweet.getUser().getName().toLowerCase().contains(whitelister)) {
                    response.setStatus(50);
                    break;
                }
            }
            responseList.add(response);
        }
        return responseList;

    }
}
