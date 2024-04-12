package com.wms.wms.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseSuccess extends ResponseEntity<ResponseData> {

    // PUT, PATCH, DELETE
    public ResponseSuccess(HttpStatus status, String message) {
        super(new ResponseData(ResponseData.RESPONSE_STATUS_SUCCESS, status, message), HttpStatus.OK);
    }

    // GET, POST
    public ResponseSuccess(HttpStatus status, String message, Object data) {
        super(new ResponseData(ResponseData.RESPONSE_STATUS_SUCCESS, status, message, data), HttpStatus.OK);
    }

}
