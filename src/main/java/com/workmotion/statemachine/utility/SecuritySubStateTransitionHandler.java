package com.workmotion.statemachine.utility;

import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.model.stateEnums.SecuritySubState;
import com.workmotion.statemachine.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecuritySubStateTransitionHandler {

    private final EmployeeRepository employeeRepository;

    public Employee finishSecurityCheck(Employee employee) {
        if (employee.getSecuritySubState().equals(SecuritySubState.SECURITY_CHECK_STARTED)) {
            employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_FINISHED);
            employeeRepository.save(employee);
            return employee;
        }
        // throw illegal transition exception
        return null;
    }

}
