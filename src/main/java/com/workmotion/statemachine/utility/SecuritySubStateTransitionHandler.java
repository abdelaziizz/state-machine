package com.workmotion.statemachine.utility;

import com.workmotion.statemachine.exception.IllegalTransitionException;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.model.stateEnums.SecuritySubState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecuritySubStateTransitionHandler {

    public Employee finishSecurityCheck(Employee employee) {
        if (employee.getSecuritySubState().equals(SecuritySubState.SECURITY_CHECK_STARTED)) {
            employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_FINISHED);
            return employee;
        }
        throw new IllegalTransitionException();
    }

}
