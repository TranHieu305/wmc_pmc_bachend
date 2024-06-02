package com.wms.wms.advice;

import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.dto.response.ResponseError;
import com.wms.wms.exception.UniqueConstraintViolationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    /**
     * Exception handler for MethodArgumentNotValidException.
     * This method handles exceptions thrown when method arguments fail validation.
     * It returns a map containing field names and their corresponding error messages.
     *
     * @param exception The MethodArgumentNotValidException instance.
     * @return A map containing field names and their error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleInvalidArgument(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));

        return  new ResponseError(HttpStatus.BAD_REQUEST, "Data is not valid", errorMap);
    }

    /**
     * Exception handler for MethodArgumentTypeMismatchException.
     *
     * @param exception The MethodArgumentTypeMismatchException that was thrown.
     * @return A ResponseError containing the exception message.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return new ResponseError(HttpStatus.BAD_REQUEST, "Argument type mismatch");
    }


    /**
     * Exception handler for ResourceNotFoundException.
     *
     * @param exception The ResourceNotFoundException that was thrown
     * @return A ResponseError containing the exception message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleObjectNotFound(ResourceNotFoundException exception) {

        return new ResponseError(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    /**
     * Exception handler for UniqueConstraintViolationException.
     *
     * @param exception The UniqueConstraintViolationException that was thrown
     * @return A ResponseError containing the exception message
     */
    @ExceptionHandler(UniqueConstraintViolationException.class)
    public ResponseEntity handleUniqueConstraintViolation(UniqueConstraintViolationException exception) {
        return new ResponseError(HttpStatus.CONFLICT, exception.getMessage());
    }

    /**
     * Exception handler for ConstraintViolationException.
     *
     * @param exception The ConstraintViolationException that was thrown
     * @return A ResponseError containing the exception message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolation(ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getConstraintViolations().forEach(violation -> errorMap.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return new ResponseError(HttpStatus.CONFLICT, "Validation error", errorMap);
    }

    @ExceptionHandler(com.wms.wms.exception.ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolation2(com.wms.wms.exception.ConstraintViolationException exception) {
        return new ResponseError(HttpStatus.CONFLICT, exception.getMessage());
    }

}
