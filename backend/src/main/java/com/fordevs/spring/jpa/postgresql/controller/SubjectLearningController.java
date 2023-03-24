package com.fordevs.spring.jpa.postgresql.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fordevs.spring.jpa.postgresql.model.SubjectLearning;
import com.fordevs.spring.jpa.postgresql.repository.SubjectLearningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@Slf4j
public class SubjectLearningController {

    @Autowired
    private SubjectLearningRepository subjectLearningRepository;

    @GetMapping(value = "/subject")
    public ResponseEntity<List<SubjectLearning>> getAllSubjects(@RequestParam(required = false) String subjectLearningName) {
        try {
            List<SubjectLearning> subjectLearning = new ArrayList<>();

            if (subjectLearningName == null)
                subjectLearning.addAll(subjectLearningRepository.findAll());

            else
                subjectLearning.addAll(subjectLearningRepository.findBySubjectLearningNameContaining(subjectLearningName));

            if (subjectLearning.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(subjectLearning, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/subject")
    public ResponseEntity<SubjectLearning> createSubject(@RequestBody String newSubjectLearning) throws JsonParseException, IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SubjectLearning subjectLearning = objectMapper.readValue(newSubjectLearning, SubjectLearning.class);

            SubjectLearning _subjectLearning = subjectLearningRepository
                    .save(subjectLearning);

            log.info("Subjects: {}", _subjectLearning);
            return new ResponseEntity<>(_subjectLearning, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Subjects: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
