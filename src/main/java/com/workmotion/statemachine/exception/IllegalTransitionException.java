package com.workmotion.statemachine.exception;

public class IllegalTransitionException extends RuntimeException {

    public IllegalTransitionException() {
    }

    public IllegalTransitionException(String message) {
        super(message);
    }

}
