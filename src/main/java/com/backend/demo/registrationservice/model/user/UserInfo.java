package com.backend.demo.registrationservice.model.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserInfo {
    private String userName;
    private String password;
    private String address;
    private BigDecimal salary;
    private String phone;
    private String email;
    private String firstName;
    private String lastName;
    private String referenceCode;
    private String memberType;
}
