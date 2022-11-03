package com.fordevs.spring.jpa.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long subject_learning_id;

    @Column(name = "subject_learning_name")
    private String subjectLearningName;

    // @Column(name = "")
    //private Long student_id;

    @Column(name = "marks_obtained")
    private String marksObtained;

    @ManyToMany//(mappedBy = "student_id")
    private List<Student> student;

}
