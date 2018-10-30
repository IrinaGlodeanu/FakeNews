package com.fakenews.app.pub_sub;

import com.fakenews.entities.User;

public class SubscriberImpl extends Subscriber {

    @Override
    public void addSubscriber(User user, PubSubService pubSubService){
        pubSubService.addSubscriber(user, this);
    }

    @Override
    public void unSubscribe(User user, PubSubService pubSubService){
        pubSubService.removeSubscriber(user, this);
    }

    @Override
    public void getTweetsForSubscriberOfUser(User user, PubSubService pubSubService) {
        pubSubService.getTweetsForSubscriberOfUser(user, this);
    }
}
