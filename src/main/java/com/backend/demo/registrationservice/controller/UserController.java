package com.backend.demo.registrationservice.controller;

import com.backend.demo.registrationservice.model.request.LoginRequest;
import com.backend.demo.registrationservice.model.request.RegisUserRequest;
import com.backend.demo.registrationservice.model.response.GetUserInfoResponse;
import com.backend.demo.registrationservice.model.response.LoginResponse;
import com.backend.demo.registrationservice.model.response.RegisUserResponse;
import com.backend.demo.registrationservice.model.response.ResponseModel;
import com.backend.demo.registrationservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/user/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel<RegisUserResponse>> regisUser(@RequestBody @Valid RegisUserRequest request) {
        ResponseModel<RegisUserResponse> response = new ResponseModel<>();
        response.setStatus("SUCCESS");
        response.setData(userService.regisUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/user/login")
    public ResponseEntity<ResponseModel<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        ResponseModel<LoginResponse> response = new ResponseModel<>();
        response.setStatus("SUCCESS");
        response.setData(userService.login(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/user/info")
    public ResponseEntity<ResponseModel<GetUserInfoResponse>> getUserInfo(@RequestParam(name = "userName") @NotBlank @Size(min = 4, max = 50) String userName) {
        ResponseModel<GetUserInfoResponse> response = new ResponseModel<>();
        response.setStatus("SUCCESS");
        response.setData(userService.getUserInfo(userName));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
