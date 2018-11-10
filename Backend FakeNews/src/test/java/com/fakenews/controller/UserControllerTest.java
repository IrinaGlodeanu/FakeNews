package com.fakenews.controller;

import com.fakenews.entities.Tweet;
import com.fakenews.entities.User;
import com.fakenews.repository.UserRepository;
import com.fakenews.service.UserService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private UserController userController;

    private User fakeUser;
    private User trueUser;

    @BeforeEach
    void setUp() {

        this.fakeUser = createUserWithTrustDegree(0.2);

        this.trueUser = createUserWithTrustDegree(0.7);

        when(userRepository.findAll()).thenReturn(Lists.newArrayList(fakeUser, trueUser));

        this.userController = new UserController();
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

    @Test
    public void shouldReturnUsersWhenCallGetUserFunction(){
        //Act
        List<User> users = this.userController.getUser().getBody();

        //Assert
        assertThat(users.size()).isEqualTo(2);

    }

}
