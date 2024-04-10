package com.wms.wms.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseError extends ResponseEntity<ResponseData> {
    public ResponseError(int status, String message) {
        super(new ResponseData(status, message), HttpStatus.BAD_REQUEST);
    }

    public ResponseError(int status, String message, Object data) {
        super(new ResponseData(status, message, data), HttpStatus.BAD_REQUEST);
    }
}
