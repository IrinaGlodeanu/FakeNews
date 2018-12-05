package com.fakenews.service;

import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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

    public Status getTweetById(long id) throws TwitterException {
        Twitter twitter = twitterConnectionService.getTwitterInstance();

        return twitter.showStatus(id);

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
