package com.fordevs.spring.jpa.postgresql.repository;

import com.fordevs.spring.jpa.postgresql.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Page<Student> findByFirstNameContaining(String firstName, Pageable pageable);
  Page<Student> findAll(Pageable pageable);

}
