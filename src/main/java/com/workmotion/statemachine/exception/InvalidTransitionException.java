package com.workmotion.statemachine.exception;

public class InvalidTransitionException extends RuntimeException {
    public InvalidTransitionException() {

    }

    public InvalidTransitionException(String message) {
        super(message);
    }
}
