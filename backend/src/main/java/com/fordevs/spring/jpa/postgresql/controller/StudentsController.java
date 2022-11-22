package com.fordevs.spring.jpa.postgresql.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fordevs.spring.jpa.postgresql.model.Student;
import com.fordevs.spring.jpa.postgresql.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class StudentsController {

    @Autowired
    private StudentRepository studentRepository;

//    List<String> subjectLearningList = new ArrayList<>();
//    JsonNode subjectLearning;
//    JsonNode departments;


    //	getting all users
    //@GetMapping(value = "/students", produces = "application/json")
    @GetMapping(value = "/students")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String fullName) {
        try {
            List<Student> studentList = new ArrayList<>();

            if (fullName == null)
                studentList.addAll(studentRepository.findAll());

            else
                studentList.addAll(studentRepository.findByFullNameContaining(fullName));

            if (studentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //	getting users by id
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentsById(@PathVariable("id") long id) {
        Optional<Student> studentsData = studentRepository.findById(id);

        return studentsData.map(student -> new ResponseEntity<>(student, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //	Create Student
    // @PostMapping(value = "/students", consumes = "application/json")
    @PostMapping(value = "/students")
    public ResponseEntity<Student> createStudents(@RequestBody String newStudent) throws JsonParseException, JsonMappingException, IOException {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
//            //objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            Student student = objectMapper.readValue(newStudent, Student.class);
            //JsonNode jsonNode = objectMapper.readTree(String.valueOf(student));

//            departments = jsonNode.at("/department");
//            subjectLearning = jsonNode.at("/subjectLearning");
//            subjectLearningList.add(String.valueOf(subjectLearning));


            Student _student = studentRepository.save(student);

            log.info("Student: {}", _student);
            // log.info("Subject Learning: {}", subjectLearningList);
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info(String.valueOf(e));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //	Update Student
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
        Optional<Student> studentsData = studentRepository.findById(id);

        if (studentsData.isPresent()) {
            Student _student = studentsData.get();
            _student.setFullName(student.getFullName());
            _student.setEmail(student.getEmail());
            _student.setPhone(student.getPhone());
            _student.setBirthDate(student.getBirthDate());
            _student.setIsActive(student.getIsActive());
            _student.setSubjectLearning(student.getSubjectLearning());
            _student.setDepartment(student.getDepartment());
            return new ResponseEntity<>(studentRepository.save(_student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete Student
    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudents(@PathVariable("id") long id) {
        try {
            studentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete All Student
    @DeleteMapping("/students")
    public ResponseEntity<HttpStatus> deleteAllStudents() {
        try {
            studentRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
