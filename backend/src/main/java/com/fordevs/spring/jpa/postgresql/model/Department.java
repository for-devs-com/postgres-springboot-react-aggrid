package com.fordevs.spring.jpa.postgresql.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "departments")
@Data
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dept_id", nullable = false)
    Long deptID;

    @Column(name = "dept_name")
    String deptName;

    // Fk
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Student> students = new ArrayList<>();

    public Department() {

    }

    public Department(String deptName, List<Student> students) {
        this.deptName = deptName;
        this.students = students;
    }
}
