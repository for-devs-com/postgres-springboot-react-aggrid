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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "student_generator")
    //@Column(name = "student_id", nullable = false, unique = true)
    @Column(name = "id", unique = true)
    Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

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

    public Student(String firstName, String lastName ,String email, Boolean isActive, Department department, List<SubjectLearning> subjectLearning) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isActive = isActive;
        this.department = department;
        this.subjectLearning = subjectLearning;
    }
}

