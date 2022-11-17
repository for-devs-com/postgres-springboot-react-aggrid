package com.fordevs.spring.jpa.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject_learning")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_learning_id", unique = true)
    Long subjectLearningID;

    @Column(name = "subject_learning_name")
    String subjectLearningName;

    @Column(name = "marks_obtained")
    String marksObtained;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "subjectlearnings_students",
            joinColumns = @JoinColumn(name = "subject_learning_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    List<Student> students;
}
