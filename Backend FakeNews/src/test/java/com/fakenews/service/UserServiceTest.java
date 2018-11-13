package com.fakenews.service;


import com.fakenews.entities.Tweet;
import com.fakenews.entities.User;
import com.fakenews.repository.TweetRepository;
import com.fakenews.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private UserService userService;

    private User fakeUser;
    private User trueUser;
    private User fakeUser2;
    private User trueUser2;

    @BeforeEach
    void setUp() {

        this.fakeUser = createUserWithTrustDegree(0.2);
        this.fakeUser2 = createUserWithTrustDegree(0.0);

        this.trueUser = createUserWithTrustDegree(0.7);
        this.trueUser2 = createUserWithTrustDegree(0.9);

        when(userRepository.findAll()).thenReturn(Lists.newArrayList(fakeUser, fakeUser2, trueUser, trueUser2));

        this.userService = new UserService(this.userRepository);
    }

    @Test
    public void shouldGetUsersWithTrustDegreeLowerThanPoint5WhenSearchingFakeUsers(){
        //Act
        List<User> fakeUsers = userService.getFakeUsers();

        //Assert
        assertThat(fakeUsers.size()).isEqualTo(2);
        assertThat(fakeUsers).containsExactly(fakeUser, fakeUser2);
    }

    @Test
    public void shouldGetUsersWithTrustDegreeBiggerThanPoint5WhenSearchingTrueUsers(){
        //Act
        List<User> trueUsers = userService.getTrueUsers();

        //Assert
        assertThat(trueUsers.size()).isEqualTo(2);
        assertThat(trueUsers).containsExactly(trueUser, trueUser2);
    }

    @Test
    void shouldSaveUserWithAllFields() {

        User user = createUserWithTrustDegree(0.7);

        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(user);

        userService.save(user);

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(user).isEqualToComparingFieldByFieldRecursively(capturedUser);
    }

    private User createUserWithTrustDegree(Double trustDegree){

        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setEmail("mail@yahoo.com");
        user.setUsername("username");
        user.setTrustDegree(trustDegree);
        user.setRegistrationDate(LocalDateTime.now());
        user.setTweets(new ArrayList<Tweet>());

        return user;

    }
}
