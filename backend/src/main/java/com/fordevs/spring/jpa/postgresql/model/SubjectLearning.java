package com.fordevs.spring.jpa.postgresql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "subject_learning")
@Data
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "subjectLearningID")
public class SubjectLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "subject_learning_generator")
    @Column(name = "subject_learning_id")
    Long subjectLearningID;

    @Column(name = "subject_learning_name")
    String subjectLearningName;

    @Column(name = "marks_obtained")
    Long marksObtained;

//    if many to many realionship needed, this shuld work
//
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "subjectlearnings_students",
//            joinColumns = @JoinColumn(name = "subject_learning_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id")
//    )

    //@JoinColumn(name = "student_id")
    @ManyToOne
    @JoinColumn(name = "student_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    //@JsonIgnore
    Student students;

    public SubjectLearning() {
    }

    public SubjectLearning(String subjectLearningName, Long marksObtained, Student students) {
        this.subjectLearningName = subjectLearningName;
        this.marksObtained = marksObtained;
        this.students = students;
    }
}
