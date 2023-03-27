package com.fordevs.spring.jpa.postgresql.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fordevs.spring.jpa.postgresql.model.Student;
import com.fordevs.spring.jpa.postgresql.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
//@RequiredArgsConstructor
@Slf4j
public class StudentsController {

    private final StudentRepository studentRepository;


    public StudentsController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    //	getting all users
    @GetMapping(value = "/students")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String fullName) {
        try {
            //ObjectMapper mapper = new ObjectMapper();
            List<Student> studentList = new ArrayList<>();
            //Student students = mapper.;

            if (fullName == null)
                studentList.addAll(studentRepository.findAll());


            else
                studentList.addAll(studentRepository.findByFullNameContaining(fullName));



            log.info("Student list: {}", studentList);

            /*método "trim()" es para eliminar cualquier espacio en blanco adicional en . Si el valor es una cadena vacía,
            se devuelve un código de estado HTTP 400 (Solicitud incorrecta) en lugar de continuar con la función.*/
            if (fullName != null && fullName.trim().length() == 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }



            return new ResponseEntity<>(studentList, HttpStatus.OK);
        }
        catch (Exception e) {
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
    @PostMapping(value = "/students")
    public ResponseEntity<Student> createStudents(@RequestBody String newStudent) throws JsonParseException, IOException {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(newStudent, Student.class);
            Student _student = studentRepository.save(student);
            log.info("Student content: {}", _student);

            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            log.error("Error while creating student: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
            //_student.setSubjectLearning(student.getSubjectLearning());
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
