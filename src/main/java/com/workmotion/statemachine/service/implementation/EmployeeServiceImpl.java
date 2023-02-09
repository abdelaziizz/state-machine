package com.workmotion.statemachine.service.implementation;

import com.workmotion.statemachine.model.dto.EmployeeDTO;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.model.stateEnums.SecuritySubState;
import com.workmotion.statemachine.model.stateEnums.State;
import com.workmotion.statemachine.model.stateEnums.WorkPermitSubState;
import com.workmotion.statemachine.repository.EmployeeRepository;
import com.workmotion.statemachine.service.framework.EmployeeService;
import com.workmotion.statemachine.utility.TransitionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TransitionHandler transitionHandler;

    @Override
    public Employee create(EmployeeDTO employeeDTO) throws SQLIntegrityConstraintViolationException {
        ModelMapper modelMapper = new ModelMapper();
        try {
            Employee employee = modelMapper.map(employeeDTO, Employee.class);
            employee.setState(State.ADDED);
            employee.setSecuritySubState(SecuritySubState.NONE);
            employee.setWorkPermitSubState(WorkPermitSubState.NONE);
            employeeRepository.save(employee);
            log.info("Successfully added employee to database with id : " + employee.getId());
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLIntegrityConstraintViolationException();
        }
    }

    @Override
    public Employee findById(int id) throws ChangeSetPersister.NotFoundException {
        return employeeRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public Employee transition(int id, String transition) throws ChangeSetPersister.NotFoundException {
        Employee employee = findById(id);
        return transitionHandler.routeTransition(employee, transition);
    }
}
