package com.fordevs.spring.jpa.postgresql.controller;

import com.fordevs.spring.jpa.postgresql.model.Student;
import com.fordevs.spring.jpa.postgresql.model.SubjectLearning;
import com.fordevs.spring.jpa.postgresql.repository.SubjectLearningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class SubjectLearningController {

   @Autowired private SubjectLearningRepository subjectLearningRepository;

    @GetMapping("/subject")
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
    public ResponseEntity<SubjectLearning> createSubject(@RequestBody SubjectLearning subjectLearning) {
        try {
            SubjectLearning _subjectLearning = subjectLearningRepository
                    .save(subjectLearning);
            return new ResponseEntity<>(_subjectLearning, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
