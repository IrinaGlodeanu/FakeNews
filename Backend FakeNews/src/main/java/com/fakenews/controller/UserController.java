package com.fakenews.controller;

import com.fakenews.entities.User;
import com.fakenews.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUser() {
        List<User> result = Lists.newArrayList(userService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
