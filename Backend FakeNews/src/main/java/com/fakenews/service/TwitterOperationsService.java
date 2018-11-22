package com.fakenews.service;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterOperationsService {

    private TwitterConnectionService twitterConnectionService;

    public TwitterOperationsService() {
        twitterConnectionService = new TwitterConnectionService();
    }

    public List<String> getTimeLine() throws TwitterException {
        Twitter twitter = twitterConnectionService.getTwitterInstance();

        return twitter.getHomeTimeline().stream()
                .map(item -> item.getText())
                .collect(Collectors.toList());
    }

    public List<Status> getAUsersTimelineByUsername(String username) {

        Twitter twitter = twitterConnectionService.getTwitterInstance();
        try {
            List<Status> statuses = new ArrayList<Status>();

            if (!username.isEmpty()) {
                statuses = twitter.getUserTimeline(username);
            }

            return statuses;

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        return new ArrayList<Status>();
    }

    public List<Status> getTimeLineTweets() throws TwitterException {
        Twitter twitter = twitterConnectionService.getTwitterInstance();
        return twitter.getHomeTimeline();
    }

    public static void main(String[] args) throws TwitterException {
        TwitterOperationsService twitterOperationsService = new TwitterOperationsService();
//        twitterOperationsService.getAUsersTimelineByUsername("itudor10");
        List<Status> statuses = twitterOperationsService.getTimeLineTweets();
        System.out.println(statuses);
    }

}
