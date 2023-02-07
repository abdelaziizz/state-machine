package com.workmotion.statemachine.controller;

import com.workmotion.statemachine.model.dto.EmployeeDTO;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.service.Framework.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeDTO employeeDTO) throws SQLIntegrityConstraintViolationException {
        return new ResponseEntity<>(employeeService.create(employeeDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) throws ChangeSetPersister.NotFoundException {
        return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
    }

}
