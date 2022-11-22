package com.fordevs.spring.jpa.postgresql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "student_id", nullable = false, unique = true)
    @Column(name = "student_id", unique = true)
    Long studentID;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "is_active")
    private Boolean isActive;

    // FK
    // Department department = new Department();
    @ManyToOne
    @JoinColumn(name = "dept_id")
    @JsonBackReference
    Department department;

    @ManyToMany(mappedBy = "students")
    List<SubjectLearning> subjectLearning = new ArrayList<>();

    public Student() {
    }



}

