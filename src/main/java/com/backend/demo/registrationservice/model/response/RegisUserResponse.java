package com.backend.demo.registrationservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisUserResponse {
    private String userName;
    private String memberType;
    private String referenceCode;
}
