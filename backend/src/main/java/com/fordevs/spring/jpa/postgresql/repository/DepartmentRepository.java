package com.fordevs.spring.jpa.postgresql.repository;

import com.fordevs.spring.jpa.postgresql.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByDeptName(String deptName);
}
