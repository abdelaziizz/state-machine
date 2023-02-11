package com.workmotion.statemachine.utility;

import com.workmotion.statemachine.exception.IllegalTransitionException;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.model.stateEnums.WorkPermitSubState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkPermitSubStateTransitionHandler {

    public Employee awaitWorkPermitVerification(Employee employee) {
        if (employee.getWorkPermitSubState().equals(WorkPermitSubState.WORK_PERMIT_CHECK_STARTED)) {
            employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_PENDING_VERIFICATION);
            return employee;
        }
        throw new IllegalTransitionException();
    }

    public Employee finishWorkPermitCheck(Employee employee) {
        if (employee.getWorkPermitSubState().equals(WorkPermitSubState.WORK_PERMIT_PENDING_VERIFICATION)) {
            employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_CHECK_FINISHED);
            return employee;
        }
        throw new IllegalTransitionException();
    }

}
