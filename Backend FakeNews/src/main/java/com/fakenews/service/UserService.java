package com.fakenews.service;
import com.fakenews.entities.Tweet;
import com.fakenews.entities.User;
import com.fakenews.repository.TweetRepository;
import com.fakenews.repository.UserRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getFakeUsers() {
        return Lists.newArrayList(userRepository.findAll())
                .stream()
                .filter(User::isFake)
                .collect(Collectors.toList());
    }

    public List<User> getTrueUsers() {
        return Lists.newArrayList(userRepository.findAll())
                .stream()
                .filter(User::isTrue)
                .collect(Collectors.toList());
    }

    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
