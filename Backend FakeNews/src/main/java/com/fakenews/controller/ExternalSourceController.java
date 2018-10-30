package com.fakenews.controller;

import com.fakenews.entities.ExternalSource;
import com.fakenews.repository.ExternalSourceRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/externalSources")
public class ExternalSourceController {

    @Autowired
    private ExternalSourceRepository externalSourceRepository;

    @GetMapping
    public ResponseEntity<List<ExternalSource>> getExternalSource() {
        List<ExternalSource> result = Lists.newArrayList(externalSourceRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addExternalSource(@RequestBody ExternalSource externalSource) {
        externalSourceRepository.save(externalSource);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
