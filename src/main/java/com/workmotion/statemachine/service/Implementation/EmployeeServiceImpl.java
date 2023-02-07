package com.workmotion.statemachine.service.Implementation;

import com.workmotion.statemachine.model.dto.EmployeeDTO;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.repository.EmployeeRepository;
import com.workmotion.statemachine.service.Framework.EmployeeService;
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

    @Override
    public Employee create(EmployeeDTO employeeDTO) throws SQLIntegrityConstraintViolationException {
        ModelMapper modelMapper = new ModelMapper();
        try {
            Employee employee = employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
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
}
