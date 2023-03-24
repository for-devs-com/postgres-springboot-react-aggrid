package com.fordevs.spring.jpa.postgresql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "studentID")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "student_generator")
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

    @OneToMany(mappedBy = "students", cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
    } )
    @JsonManagedReference
    List<SubjectLearning> subjectLearning = new ArrayList<>();

    public Student() {
    }

    public Student(String fullName, String email, String phone, String birthDate, Boolean isActive, Department department, List<SubjectLearning> subjectLearning) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.isActive = isActive;
        this.department = department;
        this.subjectLearning = subjectLearning;
    }
}

