package com.backend.demo.registrationservice.service;

import com.backend.demo.registrationservice.config.security.JwtTokenProvider;
import com.backend.demo.registrationservice.exception.CommonException;
import com.backend.demo.registrationservice.model.ErrorCodeEnum;
import com.backend.demo.registrationservice.model.request.LoginRequest;
import com.backend.demo.registrationservice.model.request.RegisUserRequest;
import com.backend.demo.registrationservice.model.response.GetUserInfoResponse;
import com.backend.demo.registrationservice.model.response.LoginResponse;
import com.backend.demo.registrationservice.model.response.RegisUserResponse;
import com.backend.demo.registrationservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
            String token = jwtTokenProvider.createToken(request.getUserName());
            return new LoginResponse(token);
        } catch (AuthenticationException e) {
            log.error("Authentication error", e);
            throw new CommonException(ErrorCodeEnum.ER001, HttpStatus.UNAUTHORIZED);
        }
    }

    public RegisUserResponse regisUser(RegisUserRequest request) {
        if (request.getSalary().compareTo(BigDecimal.valueOf(15000)) < 0){
            throw new CommonException(ErrorCodeEnum.ER003, HttpStatus.CONFLICT);
        }
        if (userRepository.checkUserExist(request.getUserName())) {
            throw new CommonException(ErrorCodeEnum.ER002, HttpStatus.CONFLICT);
        }
        LocalDate now = LocalDate.now();
        DateTimeFormatter refCodeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String referenceCode = now.format(refCodeFormatter) + request.getPhone().substring(request.getPhone().length() - 4);
        String memberType;
        if (request.getSalary().compareTo(BigDecimal.valueOf(50000)) > 0) {
            memberType = "Platinum";
        } else if (request.getSalary().compareTo(BigDecimal.valueOf(30000)) >= 0 && request.getSalary().compareTo(BigDecimal.valueOf(50000)) <= 0) {
            memberType = "Gold";
        } else {
            memberType = "Silver";
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        int result = userRepository.saveUserInfo(request, referenceCode, memberType);
        if (result > 0) {
            return new RegisUserResponse(request.getUserName(), memberType, referenceCode);
        } else {
            throw new CommonException(ErrorCodeEnum.ER004, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public GetUserInfoResponse getUserInfo(String userName) {
        return new GetUserInfoResponse(userRepository.getUserInfo(userName));
    }
}
