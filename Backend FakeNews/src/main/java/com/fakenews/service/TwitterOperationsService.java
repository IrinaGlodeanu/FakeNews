package com.fakenews.service;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public void getAUsersTimelineByUsername(String username) {

        Twitter twitter = twitterConnectionService.getTwitterInstance();
        try {
            List<Status> statuses;

            if (username.isEmpty()) {
                statuses = twitter.getUserTimeline(username);
            } else {
                username = twitter.verifyCredentials().getScreenName();
                statuses = twitter.getUserTimeline();
            }

            System.out.println("Showing @" + username + "'s user timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }

    public List<Status> getTimeLineTweets() throws TwitterException {
        Twitter twitter = twitterConnectionService.getTwitterInstance();
        return twitter.getHomeTimeline();
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static void main(String[] args) throws TwitterException {
        TwitterOperationsService twitterOperationsService = new TwitterOperationsService();
        twitterOperationsService.getAUsersTimelineByUsername("itudor10");
//        twitterOperationsService.getTimeLine();
    }

}
