package com.fakenews.app.decorator;

import com.fakenews.entities.Tweet;

public class TweetDecorator extends Tweet {

    public TweetDecorator() {
    }

    @Override
    public void printStatus() {
        System.out.print("True Tweet");
    }

}
