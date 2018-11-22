package com.fakenews.AOP;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AOPLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(AOPLogger.class);

    // Tweet Controller
    // Add tweet
    @Before("execution(* com.fakenews.controller.TweetController.addTweet()) && args()")
    public void beforeAddTweet() {
        LOGGER.info("TweetController - Started adding new tweet");
    }

    @Pointcut("execution(* com.fakenews.controller.TweetController.addTweet()) && args()")

    @After("execution(* com.fakenews.controller.TweetController.addTweet()) && args()")
    public void afterAddTweet() {
        LOGGER.info("TweetController - Successfully added the new tweet");
    }

    // Get all tweets
    @Before("execution(* com.fakenews.controller.TweetController.getTweet()) && args()")
    public void beforeGetTweet() {
        LOGGER.info("TweetController - Started getting all tweets");
    }

    @Pointcut("execution(* com.fakenews.controller.TweetController.getTweet()) && args()")

    @After("execution(* com.fakenews.controller.TweetController.getTweet()) && args()")
    public void afterGetTweet() {
        LOGGER.info("TweetController - Successfully got all tweets");
    }

    /// Tweet Service
    // Fake
    @Before("execution(* com.fakenews.service.TweetService.getFakeTweets()) && args()")
    public void beforeGetFakeTweets() {
        LOGGER.info("TweetService - Started getting all fake tweets");
    }

    @Pointcut("execution(* com.fakenews.service.TweetService.getFakeTweets()) && args()")

    @After("execution(* com.fakenews.service.TweetService.getFakeTweets()) && args()")
    public void afterGetFakeTweets() {
        LOGGER.info("TweetService - Successfully got all fake tweets");
    }

    // True
    @Before("execution(* com.fakenews.service.TweetService.getTrueTweets()) && args()")
    public void beforeGetTrueTweets() {
        LOGGER.info("TweetService - Started getting all true tweets");
    }

    @Pointcut("execution(* com.fakenews.service.TweetService.getTrueTweets()) && args()")

    @After("execution(* com.fakenews.service.TweetService.getTrueTweets()) && args()")
    public void afterGetTrueTweets() {
        LOGGER.info("TweetService - Successfully got all true tweets");
    }

    // All
    @Before("execution(* com.fakenews.service.TweetService.findAll()) && args()")
    public void beforeGetAllTweets() {
        LOGGER.info("TweetService - Started getting all tweets");
    }

    @Pointcut("execution(* com.fakenews.service.TweetService.findAll()) && args()")

    @After("execution(* com.fakenews.service.TweetService.findAll()) && args()")
    public void afterGetAllTweets() { LOGGER.info("TweetService - Successfully got all tweets"); }

    /// Security Service
    // Crypt
    @Before("execution(* com.fakenews.service.SecurityService.encryptString()) && args()")
    public void beforeEncryptString() { LOGGER.info("SecurityService - Started encrypting the given string"); }

    @Pointcut("execution(* com.fakenews.service.SecurityService.encryptString()) && args()")

    @After("execution(* com.fakenews.service.SecurityService.encryptString()) && args()")
    public void afterEncryptString() { LOGGER.info("SecurityService - Successfully encrypted the given string"); }

    // Decrypt
    @Before("execution(* com.fakenews.service.SecurityService.decryptString()) && args()")
    public void beforeDecryptString() {
        LOGGER.info("SecurityService - Successfully decrypted the given string");
    }

    @Pointcut("execution(* com.fakenews.service.SecurityService.decryptString()) && args()")

    @After("execution(* com.fakenews.service.SecurityService.decryptString()) && args()")
    public void afterDecryptString() { LOGGER.info("SecurityService - Successfully decrypted the given string"); }
}