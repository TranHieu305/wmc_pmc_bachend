package com.wms.wms.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class ResponseData<T> {
    public static final boolean RESPONSE_STATUS_SUCCESS = true;
    public static final boolean RESPONSE_STATUS_FAIL = false;

    private final Boolean success;
    private final HttpStatus status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;



    // PUT, PATCH, DELETE
    public ResponseData(Boolean success, HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

    // GET, POST
    public ResponseData(Boolean success, HttpStatus status, String message, T data) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Boolean getSuccess() {
        return  success;
    }
}
