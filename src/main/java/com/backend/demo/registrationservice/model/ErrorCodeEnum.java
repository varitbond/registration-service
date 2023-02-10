package com.backend.demo.registrationservice.model;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    ER001("ER001", "Invalid username or password"),
    ER002("ER002", "Username is already in use"),
    ER003("ER003", "Salary is less than 15000"),
    ER004("ER004", "Can't register user"),
    ER005("ER005", "Expired or invalid Authorization");
    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
