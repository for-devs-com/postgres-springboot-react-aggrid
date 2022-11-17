package com.fordevs.spring.jpa.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.*;

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
    @NonNull
    private String deptName;

    // Fk
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();
}
