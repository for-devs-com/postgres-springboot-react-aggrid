package com.fordevs.spring.jpa.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "student_id", nullable = false, unique = true)
    @Column(name = "student_id", unique = true)
    private Long studentID;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dob")
    private String dob;

    @Column(name = "is_active")
    private Boolean isActive;

    // FK
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentID")
    Department department;

    @ManyToMany(cascade = CascadeType.ALL)
    //@JsonManagedReference
    @JoinTable(
            name = "students_subjectlearnings",
            joinColumns = @JoinColumn(name = "sudent_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_learning_id")
    )
    private List<SubjectLearning> subjectLearning;
}

