package com.workmotion.statemachine.utility;

import com.workmotion.statemachine.exception.IllegalTransitionException;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.model.stateEnums.SecuritySubState;
import com.workmotion.statemachine.model.stateEnums.State;
import com.workmotion.statemachine.model.stateEnums.WorkPermitSubState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateTransitionHandler {

    public Employee check(Employee employee) {
        if (employee.getState().equals(State.ADDED)) {
            employee.setState(State.IN_CHECK);
            employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_STARTED);
            employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_CHECK_STARTED);
            return employee;
        }
        throw new IllegalTransitionException();
    }

    public Employee activate(Employee employee) {
        if (employee.getState().equals(State.APPROVED)) {
            employee.setState(State.ACTIVE);
            return employee;
        }
        throw new IllegalTransitionException();
    }

}
