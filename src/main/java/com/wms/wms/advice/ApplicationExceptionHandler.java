package com.wms.wms.advice;

import com.wms.wms.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return  errorMap;
    }

    /**
     * Exception handler for ObjectNotFoundException.
     * Returns a map containing the exception message with key "message".
     *
     * @param exception The ObjectNotFoundException that was thrown
     * @return A map containing the exception message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public Map<String, String> handleObjectNotFound(ObjectNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    /**
     * Exception handler for MethodArgumentTypeMismatchException.
     * Returns a map containing the exception message with key "message".
     *
     * @param exception The MethodArgumentTypeMismatchException that was thrown.
     * @return A map containing the exception message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }
}
