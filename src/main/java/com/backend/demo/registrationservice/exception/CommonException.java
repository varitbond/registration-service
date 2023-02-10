package com.backend.demo.registrationservice.exception;

import com.backend.demo.registrationservice.model.ErrorCodeEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class CommonException extends RuntimeException {
    private String code;
    private String message;
    private HttpStatus httpStatus;

    public CommonException(ErrorCodeEnum errorCodeEnum, HttpStatus httpStatus){
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
        this.httpStatus = httpStatus;
    }
}
