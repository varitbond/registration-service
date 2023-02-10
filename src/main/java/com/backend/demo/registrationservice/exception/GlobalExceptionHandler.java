package com.backend.demo.registrationservice.exception;

import com.backend.demo.registrationservice.model.error.ErrorResponse;
import com.backend.demo.registrationservice.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ResponseModel<ErrorResponse>> handleCustomException(CommonException ex) {
        log.error("Got exception.", ex);
        ResponseModel<ErrorResponse> response = new ResponseModel<>();
        response.setStatus("FAILED");
        response.setData(new ErrorResponse(ex.getMessage()));
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel<ErrorResponse>> handleException(Exception ex) {
        log.error("Got exception.", ex);
        ResponseModel<ErrorResponse> response = new ResponseModel<>();
        response.setStatus("FAILED");
        response.setData(new ErrorResponse("Internal error"));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}
