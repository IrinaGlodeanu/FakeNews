package com.fakenews.app.pub_sub;

import com.fakenews.entities.Tweet;

public class PublisherImpl implements Publisher {

    @Override
    public void publish(Tweet tweet, PubSubService pubSubService) {
        pubSubService.addTweetToQueue(tweet);
    }
}
