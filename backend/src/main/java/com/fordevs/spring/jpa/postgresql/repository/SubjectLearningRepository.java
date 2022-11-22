package com.fordevs.spring.jpa.postgresql.repository;

import com.fordevs.spring.jpa.postgresql.model.SubjectLearning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubjectLearningRepository extends JpaRepository<SubjectLearning, Long> {
    List<SubjectLearning> findBySubjectLearningNameContaining(String subjectLearningName);
}
