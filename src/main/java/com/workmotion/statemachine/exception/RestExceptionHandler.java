package com.workmotion.statemachine.exception;

import com.workmotion.statemachine.model.api.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(HttpServletRequest req, ChangeSetPersister.NotFoundException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, req.getRequestURI());
        response.setMessage("Unable to locate this employee");
        ex.printStackTrace();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleIntegrityConstraintException(HttpServletRequest req, SQLIntegrityConstraintViolationException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, req.getRequestURI());
        response.setMessage("The contract number provided already exists for another employee");
        ex.printStackTrace();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(IllegalTransitionException.class)
    public ResponseEntity<Object> handleIllegalTransitionException(HttpServletRequest req, IllegalTransitionException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN, req.getRequestURI());
        response.setMessage("Illegal state transition");
        ex.printStackTrace();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(InvalidTransitionException.class)
    public ResponseEntity<Object> handleInvalidTransitionException(HttpServletRequest req, InvalidTransitionException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, req.getRequestURI());
        response.setMessage("Unknown state transition");
        ex.printStackTrace();
        return buildResponseEntity(response);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse response) {
        return new ResponseEntity<>(response, response.getStatus());
    }


}
