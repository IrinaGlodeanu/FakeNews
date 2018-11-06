package com.fakenews.service;

import com.fakenews.entities.Tweet;
import com.fakenews.entities.User;
import com.fakenews.repository.TweetRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;
    @Captor
    private ArgumentCaptor<Tweet> tweetArgumentCaptor;

    private TweetService tweetService;

    private Tweet fakeTweet;
    private Tweet trueTweet;
    private Tweet fakeTweet2;
    private Tweet trueTweet2;

    @BeforeEach
    void setUp() {

        this.fakeTweet = createTweetWithTrustDegree(0.2);
        this.fakeTweet2 = createTweetWithTrustDegree(0.0);

        this.trueTweet = createTweetWithTrustDegree(0.7);
        this.trueTweet2 = createTweetWithTrustDegree(0.9);

        when(tweetRepository.findAll()).thenReturn(Lists.newArrayList(fakeTweet, fakeTweet2, trueTweet, trueTweet2));

        this.tweetService = new TweetService(this.tweetRepository);
    }

    @Test
    void shouldGetTweetsWithTrustDegreeLowerThanPoint5WhenSearchingFakeTweets() {

        List<Tweet> fakeTweets = tweetService.getFakeTweets();

        assertThat(fakeTweets.size()).isEqualTo(2);
        assertThat(fakeTweets).containsExactly(fakeTweet, fakeTweet2);
    }

    @Test
    void shouldGetTweetsWithTrustDegreeHigherThanPoint5WhenSearchingTrueTweets() {

        List<Tweet> trueTweets = tweetService.getTrueTweets();

        assertThat(trueTweets.size()).isEqualTo(2);
        assertThat(trueTweets).containsExactly(trueTweet, trueTweet2);
    }

    @Test
    void shouldSaveTweetWithAllFields() {

        Tweet tweet = createTweetWithTrustDegree(0.7);

        when(tweetRepository.save(tweetArgumentCaptor.capture())).thenReturn(tweet);

        tweetService.saveTweet(tweet);

        Tweet capturedTweet = tweetArgumentCaptor.getValue();

        assertThat(tweet).isEqualToComparingFieldByFieldRecursively(capturedTweet);
    }

    private Tweet createTweetWithTrustDegree(Double trustDegree) {

        Tweet tweet = new Tweet();

        tweet.setId(UUID.randomUUID().toString());
        tweet.setAuthor(new User());
        tweet.setCreationDate(LocalDateTime.now());
        tweet.setTrustDegree(trustDegree);

        return tweet;
    }
}
