package com.fakenews.service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


@Component
public class TwitterConnectionService {

    public static final String ACCESS_TOKEN = "1054021077138051072-DKw8I6B4FL3EcDO57yweuUvErxkmXM";
    public static final String ACCESS_SECRET = "ZsY5v1eMMzr8o7KYyba6KhBE9GkqqckmxiNilc40PxHbo";
    public static final String CONSUMER_KEY = "yhnZNVnLm7cEqIe7nlPU1GH95";
    public static final String CONSUMER_SECRET = "CLushgM5g6YDBVgqLhWm5tZUWmYfhDFBfWA6IUV9tiPLQ5tXDn";

    private Configuration config;

    public void configTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_SECRET);
        config = cb.build();
    }

    public Twitter getTwitterInstance(){
        if (config == null) {
            configTwitter();
        }
        TwitterFactory factory = new TwitterFactory(config);
        Twitter twitter = factory.getInstance();
        return twitter;
    }
}
