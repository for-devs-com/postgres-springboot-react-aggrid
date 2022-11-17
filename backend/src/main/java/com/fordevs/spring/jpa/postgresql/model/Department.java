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
    Long depID;

    @Column(name = "dept_name")
    String deptName;

    // Fk
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    List<Student> students;
}
