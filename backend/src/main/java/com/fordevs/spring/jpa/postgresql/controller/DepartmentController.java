package com.fordevs.spring.jpa.postgresql.controller;

import com.fordevs.spring.jpa.postgresql.model.Department;
import com.fordevs.spring.jpa.postgresql.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getDepartments(@RequestParam(required = false) String deptName) {
        try {
            List<Department> departments = new ArrayList<>();

            if (deptName == null)
                departments.addAll(departmentRepository.findAll());

            else
                departments.addAll(departmentRepository.findByDeptName(deptName));

            if (departments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(departments, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") long deptid) {
        Optional<Department> deptData = departmentRepository.findById(deptid);

        return deptData.map(department -> new ResponseEntity<>(department, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //	Create Departments
    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@RequestBody Department departments) {
        try {
            Department _departments = departmentRepository
                    .save(departments);
            return new ResponseEntity<>(_departments, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //	Update Department
    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartments(@PathVariable("id") long id, @RequestBody Department departments) {
        Optional<Department> deptData = departmentRepository.findById(id);

        if (deptData.isPresent()) {
            Department _departments = deptData.get();
            _departments.setDeptName(departments.getDeptName());
            _departments.setStudents(departments.getStudents());
            return new ResponseEntity<>(departmentRepository.save(_departments), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete Departments
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable("id") long deptid) {
        try {
            departmentRepository.deleteById(deptid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete All Departments
    @DeleteMapping("/departments")
    public ResponseEntity<HttpStatus> deleteAllDepartments() {
        try {
            departmentRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
