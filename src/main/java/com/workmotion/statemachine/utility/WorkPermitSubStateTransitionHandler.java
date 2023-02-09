package com.workmotion.statemachine.utility;

import com.workmotion.statemachine.exception.IllegalTransitionException;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.model.stateEnums.WorkPermitSubState;
import com.workmotion.statemachine.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkPermitSubStateTransitionHandler {

    private final EmployeeRepository employeeRepository;

    public Employee awaitWorkPermitVerification(Employee employee) {
        if (employee.getWorkPermitSubState().equals(WorkPermitSubState.WORK_PERMIT_CHECK_STARTED)) {
            employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_PENDING_VERIFICATION);
            employeeRepository.save(employee);
            return employee;
        }
        throw new IllegalTransitionException();
    }

    public Employee finishWorkPermitCheck(Employee employee) {
        if (employee.getWorkPermitSubState().equals(WorkPermitSubState.WORK_PERMIT_PENDING_VERIFICATION)) {
            employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_CHECK_FINISHED);
            employeeRepository.save(employee);
            return employee;
        }
        throw new IllegalTransitionException();
    }

}
