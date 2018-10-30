package com.fakenews.app.iterator;

import com.fakenews.entities.Tweet;

import java.util.List;

public class TweetIteratorImpl implements TweetIterator {
    int index;

    private List<Tweet> tweets;

    @Override
    public boolean hasNext() {

        if (index < tweets.size()) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {

        if (this.hasNext()) {
            return tweets.get(index++);
        }
        return null;
    }
}
