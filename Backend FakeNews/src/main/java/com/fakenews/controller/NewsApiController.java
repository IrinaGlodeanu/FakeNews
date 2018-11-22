package com.fakenews.controller;

import com.fakenews.model.NewsResult;
import com.fakenews.service.NewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/newsApi")
public class NewsApiController {

    @Autowired
    private NewsApiService service;

    @GetMapping
    public ResponseEntity<NewsResult> writeInFileAfterClose(@RequestParam("query") String query) {

        return new ResponseEntity<>(service.queryNews(query), HttpStatus.OK);
    }
}
