package com.workmotion.statemachine.exception;

import com.workmotion.statemachine.model.api.ErrorResponse;
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

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(HttpServletRequest req, ChangeSetPersister.NotFoundException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, req.getRequestURI());
        response.setMessage("Unable to locate this employee");
        return buildResponseEntity(response);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleIntegrityConstraintException(HttpServletRequest req,  SQLIntegrityConstraintViolationException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, req.getRequestURI());
        response.setMessage("The contract number provided already exists for another employee");
        return buildResponseEntity(response);
    }

    private ResponseEntity<Object> buildResponseEntity (ErrorResponse response) {
        return new ResponseEntity<>(response, response.getStatus());
    }



}