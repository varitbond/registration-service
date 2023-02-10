package com.backend.demo.registrationservice.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotBlank
    @Size(min = 4, max = 50)
    private String userName;
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
}
