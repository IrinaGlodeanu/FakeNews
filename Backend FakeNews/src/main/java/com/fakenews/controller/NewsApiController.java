package com.fakenews.controller;

import com.fakenews.service.NewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/newsApi")
public class NewsApiController {

    @Autowired
    private NewsApiService service ;

    @PostMapping
    public ResponseEntity<String> writeInFile(String id) throws IOException {
        service.writeApiResponseInFile(id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/after")
    public ResponseEntity<String> writeInFileAfterClose(String id) throws IOException {
        service.writeApiResponseInFileAfterClose(id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
