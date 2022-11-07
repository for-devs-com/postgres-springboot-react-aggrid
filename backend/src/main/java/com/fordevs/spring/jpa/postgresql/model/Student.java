package com.fordevs.spring.jpa.postgresql.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false, unique = true)
    private Long studentID;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dob")
    private String dob;

//    @Column(name = "dept_id")
//    private Long deptID;

    // FK
//	@Column(name = "subject_learning_id")
//    private Long subject_learning_id;

    @Column(name = "is_active")
    private Boolean isActive;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "dept_id", insertable = false, updatable = false)
    @JoinColumn(name = "dept_id")
    private Department department;

    @ManyToMany
    private List<SubjectLearning> subjectLearning = new ArrayList<>();
}

