package com.backend.demo.registrationservice.model.response;

import com.backend.demo.registrationservice.model.user.UserInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserInfoResponse {
    private String userName;
    private String address;
    private BigDecimal salary;
    private String phone;
    private String email;
    private String firstName;
    private String lastName;
    private String referenceCode;
    private String memberType;

    public GetUserInfoResponse(UserInfo userInfo){
        this.userName = userInfo.getUserName();
        this.address = userInfo.getAddress();
        this.salary = userInfo.getSalary();
        this.phone = userInfo.getPhone();
        this.email = userInfo.getEmail();
        this.firstName = userInfo.getFirstName();
        this.lastName = userInfo.getLastName();
        this.referenceCode = userInfo.getReferenceCode();
        this.memberType = userInfo.getMemberType();
    }
}


