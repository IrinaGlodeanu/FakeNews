package com.fakenews.controller;

import com.fakenews.model.NewsResult;
import com.fakenews.model.Result;
import com.fakenews.model.TweetResponse;
import com.fakenews.model.TwitterTimelineRequest;
import com.fakenews.service.NLPService;
import com.fakenews.service.NewsApiService;
import com.fakenews.service.TwitterOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api/v1/newsApi")
public class NewsApiController {

    @Autowired
    private NewsApiService service;

    @Autowired
    private NLPService nlpService;

    @Autowired
    private TwitterOperationsService twitterOperationsService;

    static final List<String> whitelist = Arrays.asList("cnn", "nbc", "cbs","aol", "nasa", "forbes", "bbc", "abc", "washington",  "wall street", "wsj");//"times",

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

    @GetMapping("/getRelatedNews2")
    public ResponseEntity<List<Result>> getRelatedNews2(@RequestParam("twitterText") List<String> twitterText) throws IOException {


        return new ResponseEntity<>(service.queryNews2(twitterText), HttpStatus.OK);
    }

    @GetMapping("/getRelatedNewsWithStatus")
        public List<TweetResponse> getRelatedNewsWithStatus() throws TwitterException {
        List<Status> timeLineTweets =
                twitterService.getTimeLineTweets();

        List<TweetResponse> responseList = new ArrayList<>();

        for (Status tweet : timeLineTweets) {
            TweetResponse response = new TweetResponse();
            response.setTweetId(String.valueOf(tweet.getId()));
            response.setStatus(0.0);

            for (String whitelister : whitelist) {
                if (tweet.getUser().getName().toLowerCase().contains(whitelister)) {
                    response.setStatus(50.0);
                    break;
                }
            }
            responseList.add(response);
        }
        return responseList;

    }

    @PostMapping("/batchStatus")
    public List<TweetResponse> returnStatusForBatchIds(@RequestBody TwitterTimelineRequest request) throws ExecutionException, InterruptedException {

        List<CompletableFuture<TweetResponse>> allFutures = request.getListOfIds()
                .stream()
                .map(id -> CompletableFuture.supplyAsync(() -> computeScoreForTweet(id)))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[allFutures.size()]))
                .thenApply(future -> allFutures.stream()
                        .filter(Objects::nonNull)
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .get();
    }

    private TweetResponse computeScoreForTweet(String idTweet) {
        Double statusForTweet = 0.0;

        Status tweet = null;
        try {
            tweet = twitterService.getTweetById(parseLong(idTweet));
        } catch (TwitterException e) {
            return new TweetResponse(idTweet, -1.0, 0,0);
        }

        List<String> remainingWhitelisters = new ArrayList<>(whitelist);


        for (String whitelister : whitelist) {
            if (tweet.getUser().getName().toLowerCase().contains(whitelister)) {
                statusForTweet = 50.0;
                remainingWhitelisters.remove(whitelister);
                break;
            }
        }

        List<String> keywords = null;
        try {
            keywords = nlpService.retrieveKeywords(tweet.getText());
        } catch (IOException e) {
            return new TweetResponse(idTweet, -1.0, 0, 0);
        }

        List<Result> sisterNews = service.queryNews2(keywords);

        Integer nofBigPubs = 0;
        for (Result rs : sisterNews){
            for (String whitelister : remainingWhitelisters){
                if (rs.getSource().getUri().contains(whitelister)) {

                    nofBigPubs++;
                }
            }
        }

        for (Result rs : sisterNews){
            for (String whitelister : remainingWhitelisters){
                if (rs.getSource().getUri().contains(whitelister)){
                    statusForTweet+=4;
                }
                if (statusForTweet>80.0){
                    break;
                }
            }
            statusForTweet+=0.5;
        }
        return new TweetResponse(idTweet, (statusForTweet < 100.0) ? statusForTweet : 100.0 , sisterNews.size(), nofBigPubs);
    }

    
    @GetMapping("/getUserTweetsAndStatusByUsername")
    public ResponseEntity<List<TweetResponse>> getUserTweetsAndStatusByUsername(String username) {
        ArrayList<TweetResponse> result = new ArrayList<TweetResponse>();
        List<Status> list = twitterOperationsService.getAUsersTimelineByUsername(username);

        for (Status status: list) {
            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setTweetId(String.valueOf(status.getId()));
            tweetResponse.setStatus(20);
            result.add(tweetResponse);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
