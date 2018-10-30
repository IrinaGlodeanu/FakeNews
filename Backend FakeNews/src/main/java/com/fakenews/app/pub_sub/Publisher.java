package com.fakenews.app.pub_sub;

import com.fakenews.entities.Tweet;

public interface Publisher {
    void publish(Tweet tweet, PubSubService pubSubService);
}
