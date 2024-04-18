package com.wms.wms.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseSuccess extends ResponseEntity<ResponseData> {

    // PUT, PATCH, DELETE
    public ResponseSuccess(HttpStatus status, String message) {
        super(new ResponseData(status.value(), message), HttpStatus.OK);
    }

    // GET, POST
    public ResponseSuccess(HttpStatus status, String message, Object data) {
        super(new ResponseData(status.value(), message, data), HttpStatus.OK);
    }

}
