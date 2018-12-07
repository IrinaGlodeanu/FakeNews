package com.fakenews.controller;

import com.fakenews.entities.Tweet;
import com.fakenews.model.Result;
import com.fakenews.model.TweetResponse;
import com.fakenews.model.TwitterTimelineRequest;
import com.fakenews.service.NLPService;
import com.fakenews.service.NewsApiService;
import com.fakenews.service.TweetService;
import com.fakenews.service.TwitterOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api/v1/newsApi")
public class NewsApiController {

    @Autowired
    private NewsApiService newsApiService;

    @Autowired
    private NLPService nlpService;

    @Autowired
    private TwitterOperationsService twitterOperationsService;

    @Autowired
    private TweetService tweetService;

    static final List<String> whitelist = Arrays.asList("cnn", "nbc", "cbs","aol", "nasa", "forbes", "bbc", "abc", "washington",  "wall street", "wsj");//"times",

    @GetMapping("/getRelatedNews")
    public ResponseEntity<List<Result>> getRelatedNews2(@RequestParam("twitterText") List<String> twitterText) {
        return new ResponseEntity<>(newsApiService.getNewsByKeywords(twitterText), HttpStatus.OK);
    }

    @GetMapping("/getRelatedNewsWithAZeroStatus")
        public List<TweetResponse> getRelatedNewsWithAZeroStatus() throws TwitterException {
        List<Status> timeLineTweets =
                twitterOperationsService.getTimeLineTweets();

        List<TweetResponse> responseList = new ArrayList<>();

        for (Status tweet : timeLineTweets) {
            TweetResponse response = new TweetResponse(String.valueOf(tweet.getId()), 0.0, 0, 0);
            responseList.add(response);
        }
        return responseList;
    }

    @PutMapping("/updateStatusForTweet")
    public TweetResponse updateStatusForTweet(String idOfTweet) {
        return computeScoreForTweet(idOfTweet, true);
    }

    @PostMapping("/batchStatus")
    public List<TweetResponse> returnStatusForBatchIds(@RequestBody TwitterTimelineRequest request) throws ExecutionException, InterruptedException {

        List<CompletableFuture<TweetResponse>> allFutures = request.getListOfIds()
                .stream()
                .map(id -> CompletableFuture.supplyAsync(() -> computeScoreForTweet(id, false)))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[allFutures.size()]))
                .thenApply(future -> allFutures.stream()
                        .filter(Objects::nonNull)
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .get();
    }

    private TweetResponse computeScoreForTweet(String idTweet, boolean isUpdate) {


        Optional<Tweet> cachedOptionalTweet = tweetService.findById(idTweet);
        if(!isUpdate){
            if (cachedOptionalTweet.isPresent()){
                Tweet cachedTweet = cachedOptionalTweet.get();
                return new TweetResponse(String.valueOf(cachedTweet.getId()), cachedTweet.getTrustDegree(), cachedTweet.getNumberOfRelatedNews(), cachedTweet.getNumberOfRelatedBigPublications() );
            }
        }

        Double statusForTweet = 0.0;

        Status tweet ;
        try {
            tweet = twitterOperationsService.getTweetById(parseLong(idTweet));
        } catch (TwitterException e) {
            return new TweetResponse(idTweet, -1.0, 0,0);
        }

        List<String> remainingWhitelisters = new ArrayList<>(whitelist);

        statusForTweet = assignFiftyPercentToWhitelisers(statusForTweet, tweet, remainingWhitelisters);

        List<String> keywords = nlpService.retrieveKeywords(tweet.getText());

        List<Result> sisterNews = newsApiService.getNewsByKeywords(keywords);

        Integer nofBigPubs = getNumberOfBigPublications(remainingWhitelisters, sisterNews);

        statusForTweet = computeScoreAfterOtherFoundNews(statusForTweet, remainingWhitelisters, sisterNews);

        Tweet newTweet = new Tweet(idTweet, (statusForTweet < 100.0) ? statusForTweet : 100.0 , sisterNews.size(), nofBigPubs);

        tweetService.saveTweet(newTweet);

        return new TweetResponse(idTweet, (statusForTweet < 100.0) ? statusForTweet : 100.0 , sisterNews.size(), nofBigPubs);
    }

    private Double computeScoreAfterOtherFoundNews(Double statusForTweet, List<String> remainingWhitelisters, List<Result> sisterNews) {
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
        return statusForTweet;
    }

    private Double assignFiftyPercentToWhitelisers(Double statusForTweet, Status tweet, List<String> remainingWhitelisters) {
        for (String whitelister : whitelist) {
            if (tweet.getUser().getName().toLowerCase().contains(whitelister)) {
                statusForTweet = 50.0;
                remainingWhitelisters.remove(whitelister);
                break;
            }
        }
        return statusForTweet;
    }

    private Integer getNumberOfBigPublications(List<String> remainingWhitelisters, List<Result> sisterNews) {
        Integer nofBigPubs = 0;
        for (Result rs : sisterNews){
            for (String whitelister : remainingWhitelisters){
                if (rs.getSource().getUri().contains(whitelister)) {
                    nofBigPubs++;
                }
            }
        }
        return nofBigPubs;
    }


    @GetMapping("/getUserTweetsAndStatusByUsername")
    public ResponseEntity<List<TweetResponse>> getUserTweetsAndStatusByUsername(String username) {
        ArrayList<TweetResponse> result = new ArrayList<TweetResponse>();
        List<Status> list = twitterOperationsService.getAUsersTimelineByUsername(username);
        for (Status tweet: list) {
            String idTweet = String.valueOf(tweet.getId());
            TweetResponse tweetResponse = computeScoreForTweet(idTweet, false);
            result.add(tweetResponse);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
