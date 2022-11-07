package com.fordevs.spring.jpa.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dept_id")
    private Long depID;

    @Column(name = "dept_name")
    private String deptName;

    // Fk
    @Column(name = "student_id")
    private Long student_id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", targetEntity = Student.class)
    //@JoinColumn(name = "student_id")
    private List<Student> students = new ArrayList<>();
}
