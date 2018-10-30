package com.fakenews.app.pub_sub;

import com.fakenews.entities.Tweet;
import com.fakenews.entities.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Subscriber {
    private List<Tweet> subscriberTweets = new ArrayList<Tweet>();

    public List<Tweet> getSubscriberTweets() {
        return subscriberTweets;
    }

    public void setSubscriberTweets(List<Tweet> subscriberTweets) {
        this.subscriberTweets = subscriberTweets;
    }

    public abstract void addSubscriber(User user, PubSubService pubSubService);

    public abstract void unSubscribe(User user, PubSubService pubSubService);

    public abstract void getTweetsForSubscriberOfUser(User user, PubSubService pubSubService);

    public void printTweets() {
        for (Tweet Tweet : subscriberTweets) {
            System.out.println("Tweet -> " + Tweet.getAuthor() + " : " + Tweet.getText());
        }
    }
}