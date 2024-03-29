package com.workmotion.statemachine.service.framework;

import com.workmotion.statemachine.model.dto.EmployeeDTO;
import com.workmotion.statemachine.model.entity.Employee;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.sql.SQLIntegrityConstraintViolationException;

public interface EmployeeService {

    Employee create(EmployeeDTO employeeDTO) throws SQLIntegrityConstraintViolationException;

    Employee findById(int id) throws ChangeSetPersister.NotFoundException;

    Employee transition(int id, String transition) throws ChangeSetPersister.NotFoundException;

}
