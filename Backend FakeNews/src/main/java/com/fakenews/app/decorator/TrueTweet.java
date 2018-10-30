package com.fakenews.app.decorator;

public class TrueTweet extends TweetDecorator {

    @Override
    public void printStatus() {
        System.out.print("True Tweet");
    }
}
