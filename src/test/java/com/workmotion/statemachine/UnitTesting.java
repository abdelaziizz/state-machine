package com.workmotion.statemachine;

import com.workmotion.statemachine.exception.IllegalTransitionException;
import com.workmotion.statemachine.exception.InvalidTransitionException;
import com.workmotion.statemachine.model.entity.Employee;
import com.workmotion.statemachine.model.stateEnums.SecuritySubState;
import com.workmotion.statemachine.model.stateEnums.State;
import com.workmotion.statemachine.model.stateEnums.WorkPermitSubState;
import com.workmotion.statemachine.utility.SecuritySubStateTransitionHandler;
import com.workmotion.statemachine.utility.StateTransitionHandler;
import com.workmotion.statemachine.utility.TransitionHandler;
import com.workmotion.statemachine.utility.WorkPermitSubStateTransitionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitTesting {
    StateTransitionHandler stateTransitionHandler;
    SecuritySubStateTransitionHandler securitySubStateTransitionHandler;
    WorkPermitSubStateTransitionHandler workPermitSubStateTransitionHandler;
    TransitionHandler transitionHandler;
    private Employee employee;


    @BeforeEach
    void setup() {

        stateTransitionHandler = new StateTransitionHandler();
        securitySubStateTransitionHandler = new SecuritySubStateTransitionHandler();
        workPermitSubStateTransitionHandler = new WorkPermitSubStateTransitionHandler();
        transitionHandler = new TransitionHandler(stateTransitionHandler, securitySubStateTransitionHandler, workPermitSubStateTransitionHandler);
        employee = new Employee();
        employee.setId(1);
        employee.setName("Test User");
        employee.setAge(20);
        employee.setCountry("Test Country");
        employee.setContractNumber(1234);
    }

    @Test
    void shouldChangeStateFromAddedToInCheck() {
        employee.setState(State.ADDED);
        employee.setSecuritySubState(SecuritySubState.NONE);
        employee.setWorkPermitSubState(WorkPermitSubState.NONE);
        employee = stateTransitionHandler.check(employee);
        Assertions.assertEquals(State.IN_CHECK, employee.getState());
    }

    @Test
    void shouldChangeStateFromApprovedToActive() {
        employee.setState(State.APPROVED);
        employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_FINISHED);
        employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_CHECK_FINISHED);
        employee = stateTransitionHandler.activate(employee);
        Assertions.assertEquals(State.ACTIVE, employee.getState());
    }

    @Test
    void shouldChangeStateFromSecurityCheckStartedToSecurityCheckEnded() {
        employee.setState(State.IN_CHECK);
        employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_STARTED);
        employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_CHECK_STARTED);
        employee = securitySubStateTransitionHandler.finishSecurityCheck(employee);
        Assertions.assertEquals(SecuritySubState.SECURITY_CHECK_FINISHED, employee.getSecuritySubState());
    }

    @Test
    void shouldChangeStateFromWorkPermitStartedToWorkPermitPending() {
        employee.setState(State.IN_CHECK);
        employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_STARTED);
        employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_CHECK_STARTED);
        employee = workPermitSubStateTransitionHandler.awaitWorkPermitVerification(employee);
        Assertions.assertEquals(WorkPermitSubState.WORK_PERMIT_PENDING_VERIFICATION, employee.getWorkPermitSubState());
    }

    @Test
    void shouldChangeStateFromWorkPermitStartedToWorkPermitFinished() {
        employee.setState(State.IN_CHECK);
        employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_STARTED);
        employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_PENDING_VERIFICATION);
        employee = workPermitSubStateTransitionHandler.finishWorkPermitCheck(employee);
        Assertions.assertEquals(WorkPermitSubState.WORK_PERMIT_CHECK_FINISHED, employee.getWorkPermitSubState());
    }

    @Test
    void shouldChangeStateFromInCheckToApprovedAutomatically() {
        employee.setState(State.IN_CHECK);
        employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_FINISHED);
        employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_PENDING_VERIFICATION);
        employee = transitionHandler.routeTransition(employee, "finish_work_permit_check");
        Assertions.assertEquals(State.APPROVED, employee.getState());
    }

    @Test
    void shouldThrowIllegalTransitionException() {
        employee.setState(State.IN_CHECK);
        employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_STARTED);
        employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_CHECK_STARTED);
        Assertions.assertThrows(IllegalTransitionException.class, () -> stateTransitionHandler.activate(employee));
    }

    @Test
    void shouldThrowInvalidTransitionException() {
        employee.setState(State.IN_CHECK);
        employee.setSecuritySubState(SecuritySubState.SECURITY_CHECK_STARTED);
        employee.setWorkPermitSubState(WorkPermitSubState.WORK_PERMIT_CHECK_STARTED);
        Assertions.assertThrows(InvalidTransitionException.class, () -> transitionHandler.routeTransition(employee, "wrong_transition"));
    }

}
