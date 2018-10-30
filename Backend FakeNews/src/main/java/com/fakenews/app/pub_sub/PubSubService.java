package com.fakenews.app.pub_sub;

import com.fakenews.entities.Tweet;
import com.fakenews.entities.User;

import java.util.*;

public class PubSubService {
    Map<String, Set<Subscriber>> subscribersUserMap = new HashMap<>();

    Queue<Tweet> tweetsQueue = new LinkedList<>();

    public void addTweetToQueue(Tweet tweet){
        tweetsQueue.add(tweet);
    }

    public void addSubscriber(User user, Subscriber subscriber){
    }

    public void removeSubscriber(User user, Subscriber subscriber){
    }

    public void broadcast(){
    }

    public void getTweetsForSubscriberOfUser(User user, Subscriber subscriber) {
    }
}
