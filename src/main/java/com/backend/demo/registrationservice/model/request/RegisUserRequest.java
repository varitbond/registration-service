package com.backend.demo.registrationservice.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class RegisUserRequest {
    @NotBlank
    @Size(min = 4, max = 50)
    private String userName;
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
    @Size(max = 255)
    private String address;
    @NotNull
    private BigDecimal salary;
    @NotBlank
    @Size(min = 10, max = 10)
    private String phone;
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 50)
    private String firstName;
    @NotBlank
    @Size(max = 50)
    private String lastName;
}
