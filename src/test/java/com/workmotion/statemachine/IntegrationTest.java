package com.workmotion.statemachine;

import com.workmotion.statemachine.model.dto.EmployeeDTO;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.repository.EmployeeRepository;
import com.workmotion.statemachine.service.framework.EmployeeService;
import com.workmotion.statemachine.service.implementation.EmployeeServiceImpl;
import com.workmotion.statemachine.utility.TransitionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLIntegrityConstraintViolationException;

@ExtendWith(MockitoExtension.class)
public class IntegrationTest {
    @Mock
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;
    @Autowired
    TransitionHandler transitionHandler;
    EmployeeDTO employeeDTO;

    @BeforeEach
    void setup() {
        employeeService = new EmployeeServiceImpl(employeeRepository, transitionHandler);
        employeeDTO = new EmployeeDTO("Test User", 20, 1234, "Test Country");
    }

    @Test
    void thisShouldSaveEmployee() throws SQLIntegrityConstraintViolationException {
        Assertions.assertInstanceOf(Employee.class, employeeService.create(employeeDTO));
    }

    @Test
    void thisShouldThrowException() {

    }
}
