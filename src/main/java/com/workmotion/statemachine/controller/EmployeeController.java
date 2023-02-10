package com.workmotion.statemachine.controller;

import com.workmotion.statemachine.model.api.ErrorResponse;
import com.workmotion.statemachine.model.dto.EmployeeDTO;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.service.framework.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "409", description = "Contract number that belongs to an existing employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeDTO employeeDTO) throws SQLIntegrityConstraintViolationException {
        return new ResponseEntity<>(employeeService.create(employeeDTO), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve employee details using ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee fetched successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Could not find employee with this ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @GetMapping("/{employeeID}")
    public ResponseEntity<Employee> findById(@PathVariable int employeeID) throws ChangeSetPersister.NotFoundException {
        return new ResponseEntity<>(employeeService.findById(employeeID), HttpStatus.OK);
    }

    @Operation(summary = " Available state transitions : " + "CHECK,  " + "ACTIVATE,  "
            + "COMPLETE_INITIAL_WORK_PERMIT_CHECK,  " + "FINISH_WORK_PERMIT_CHECK,  " + "FINISH_SECURITY_CHECK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "State changed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "403", description = "Illegal state transition",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "Unknown state transition",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @PostMapping("/transition/{employeeID}")
    public ResponseEntity<Employee> transition(@PathVariable int employeeID, @RequestParam String transition) throws ChangeSetPersister.NotFoundException {
        return new ResponseEntity<>(employeeService.transition(employeeID, transition), HttpStatus.OK);
    }

}
