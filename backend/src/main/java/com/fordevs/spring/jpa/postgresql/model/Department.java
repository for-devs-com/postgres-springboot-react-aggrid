package com.fordevs.spring.jpa.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dept_id", unique = true)
    private Long depID;

    @Column(name = "dept_name")
    private String deptName;

    // Fk
    @OneToMany(mappedBy = "department")
    Collection<Student> students;
}
