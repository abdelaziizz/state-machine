package com.workmotion.statemachine.repository;

import com.workmotion.statemachine.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
