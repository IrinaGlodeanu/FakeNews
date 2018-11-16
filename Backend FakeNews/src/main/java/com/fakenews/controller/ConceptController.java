package com.fakenews.controller;

import com.fakenews.entities.Concept;
import com.fakenews.repository.ConceptRepository;
import com.fakenews.service.NLPService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/concepts")
public class ConceptController {

    @Autowired
    private ConceptRepository conceptRepository;

    @Autowired
    private NLPService nlpService;

    @GetMapping
    public ResponseEntity<List<Concept>> getConcept() {
        List<Concept> result = Lists.newArrayList(conceptRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addConcept(@RequestBody Concept concept) {
        conceptRepository.save(concept);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
