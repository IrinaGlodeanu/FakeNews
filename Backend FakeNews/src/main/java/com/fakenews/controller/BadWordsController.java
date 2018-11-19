package com.fakenews.controller;

import com.fakenews.service.BadWordsBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/badwords")
public class BadWordsController {

    @Autowired
    private BadWordsBanService service ;

    @PostMapping
    public ResponseEntity<String> writeInFile(String id) throws IOException {
        service.writeBadWordsUserInFile(id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/after")
    public ResponseEntity<String> writeInFileAfterClose(String id) throws IOException {
        service.writeBadWordsUserInFileAfterClose(id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
