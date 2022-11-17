package com.fordevs.spring.jpa.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Collection;
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
    @NonNull
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dob")
    private String dob;

    @Column(name = "is_active")
    @NonNull
    private Boolean isActive;

    // FK
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dept_id")
    private Department department;

    @ManyToMany(mappedBy = "students")
    private List<SubjectLearning> subjectLearning;
}

