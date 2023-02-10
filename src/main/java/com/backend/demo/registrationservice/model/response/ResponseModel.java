package com.backend.demo.registrationservice.model.response;

import lombok.Data;

@Data
public class ResponseModel<T> {
    private String status;
    private T data;
}
