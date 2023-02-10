package com.workmotion.statemachine.utility;

import com.workmotion.statemachine.exception.IllegalTransitionException;
import com.workmotion.statemachine.exception.InvalidTransitionException;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.model.stateEnums.*;
import com.workmotion.statemachine.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransitionHandler {

    private final EmployeeRepository employeeRepository;
    private final StateTransitionHandler stateTransitionHandler;
    private final SecuritySubStateTransitionHandler securitySubStateTransitionHandler;
    private final WorkPermitSubStateTransitionHandler workPermitSubStateTransitionHandler;

    public Employee checkAutomaticTransition(Employee employee) {
        if (employee.getWorkPermitSubState().equals(WorkPermitSubState.WORK_PERMIT_CHECK_FINISHED) &&
                employee.getSecuritySubState().equals(SecuritySubState.SECURITY_CHECK_FINISHED)) {
            employee.setState(State.APPROVED);
            employeeRepository.save(employee);
        }
        return employee;
    }

    public Employee routeTransition(Employee employee, String transition) {
        try {
            StateTransition stateTransition = StateTransition.valueOf(transition.toUpperCase());
            return routeStateTransition(employee, stateTransition);
        } catch (IllegalArgumentException e) {
            try {
                SecuritySubStateTransition securitySubStateTransition = SecuritySubStateTransition.valueOf(transition.toUpperCase());
                return routeSecuritySubStateTransition(employee, securitySubStateTransition);
            } catch (IllegalArgumentException ex) {
                try {
                    WorkPermitSubStateTransition workPermitSubStateTransition = WorkPermitSubStateTransition.valueOf(transition.toUpperCase());
                    return routeWorkPermitSubStateTransition(employee, workPermitSubStateTransition);
                } catch (IllegalArgumentException exe) {
                    throw new InvalidTransitionException();
                }
            }
        }
    }

    public Employee routeStateTransition(Employee employee, StateTransition transition) {
        switch (transition) {
            case CHECK:
                return stateTransitionHandler.check(employee);
            case ACTIVATE:
                return stateTransitionHandler.activate(employee);
        }
        throw new IllegalTransitionException();
    }

    public Employee routeSecuritySubStateTransition(Employee employee, SecuritySubStateTransition transition) {
        if (transition == SecuritySubStateTransition.FINISH_SECURITY_CHECK) {
            employee = securitySubStateTransitionHandler.finishSecurityCheck(employee);
            return checkAutomaticTransition(employee);
        }
        throw new IllegalTransitionException();
    }

    public Employee routeWorkPermitSubStateTransition(Employee employee, WorkPermitSubStateTransition transition) {
        switch (transition) {
            case COMPLETE_INITIAL_WORK_PERMIT_CHECK:
                return workPermitSubStateTransitionHandler.awaitWorkPermitVerification(employee);
            case FINISH_WORK_PERMIT_CHECK:
                employee = workPermitSubStateTransitionHandler.finishWorkPermitCheck(employee);
                return checkAutomaticTransition(employee);

        }
        throw new InvalidTransitionException();
    }

}
