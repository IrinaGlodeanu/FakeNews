package com.fakenews.app.decorator;

public class FakeTweet extends TweetDecorator{

    @Override
    public void printStatus() {
        System.out.print("Fake Tweet");
    }
}
