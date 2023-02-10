package com.backend.demo.registrationservice.service;

import com.backend.demo.registrationservice.config.security.JwtTokenProvider;
import com.backend.demo.registrationservice.exception.CommonException;
import com.backend.demo.registrationservice.model.request.LoginRequest;
import com.backend.demo.registrationservice.model.request.RegisUserRequest;
import com.backend.demo.registrationservice.model.response.GetUserInfoResponse;
import com.backend.demo.registrationservice.model.response.LoginResponse;
import com.backend.demo.registrationservice.model.response.RegisUserResponse;
import com.backend.demo.registrationservice.model.user.UserInfo;
import com.backend.demo.registrationservice.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private UserService userService;

    @Test
    public void login() {
        LoginRequest request = new LoginRequest();
        request.setUserName("test");
        request.setPassword("12345");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(jwtTokenProvider.createToken(anyString())).thenReturn("token");

        LoginResponse response = userService.login(request);
        assertEquals("token", response.getToken());
    }

    @Test(expected = CommonException.class)
    public void loginAuthFail() {
        LoginRequest request = new LoginRequest();
        request.setUserName("test");
        request.setPassword("12345");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(mock(AuthenticationException.class));
        try {
            userService.login(request);
        } catch (CommonException e) {
            assertEquals("ER001", e.getCode());
            throw e;
        }
    }

    @Test
    public void regisUser1() {
        RegisUserRequest request = new RegisUserRequest();
        request.setUserName("username");
        request.setPassword("password");
        request.setPhone("0812345678");
        request.setSalary(BigDecimal.valueOf(20000));
        when(userRepository.checkUserExist(anyString())).thenReturn(false);
        when(userRepository.saveUserInfo(any(), anyString(), anyString())).thenReturn(1);
        RegisUserResponse response = userService.regisUser(request);
        assertEquals("username", response.getUserName());
        assertEquals("Silver", response.getMemberType());
        LocalDate now = LocalDate.now();
        DateTimeFormatter refCodeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String refCode = now.format(refCodeFormatter) + "5678";
        assertEquals(refCode, response.getReferenceCode());
    }

    @Test
    public void regisUser2() {
        RegisUserRequest request = new RegisUserRequest();
        request.setUserName("username");
        request.setPassword("password");
        request.setPhone("0812345678");
        request.setSalary(BigDecimal.valueOf(40000));
        when(userRepository.checkUserExist(anyString())).thenReturn(false);
        when(userRepository.saveUserInfo(any(), anyString(), anyString())).thenReturn(1);
        RegisUserResponse response = userService.regisUser(request);
        assertEquals("username", response.getUserName());
        assertEquals("Gold", response.getMemberType());
        LocalDate now = LocalDate.now();
        DateTimeFormatter refCodeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String refCode = now.format(refCodeFormatter) + "5678";
        assertEquals(refCode, response.getReferenceCode());
    }

    @Test
    public void regisUser3() {
        RegisUserRequest request = new RegisUserRequest();
        request.setUserName("username");
        request.setPassword("password");
        request.setPhone("0812345678");
        request.setSalary(BigDecimal.valueOf(80000));
        when(userRepository.checkUserExist(anyString())).thenReturn(false);
        when(userRepository.saveUserInfo(any(), anyString(), anyString())).thenReturn(1);
        RegisUserResponse response = userService.regisUser(request);
        assertEquals("username", response.getUserName());
        assertEquals("Platinum", response.getMemberType());
        LocalDate now = LocalDate.now();
        DateTimeFormatter refCodeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String refCode = now.format(refCodeFormatter) + "5678";
        assertEquals(refCode, response.getReferenceCode());
    }

    @Test(expected = CommonException.class)
    public void regisUserSalaryFail() {
        RegisUserRequest request = new RegisUserRequest();
        request.setUserName("username");
        request.setPassword("password");
        request.setPhone("0812345678");
        request.setSalary(BigDecimal.valueOf(10000));
        try {
            userService.regisUser(request);
        } catch (CommonException e) {
            assertEquals("ER003", e.getCode());
            throw e;
        }
    }

    @Test(expected = CommonException.class)
    public void regisUserExist() {
        RegisUserRequest request = new RegisUserRequest();
        request.setUserName("username");
        request.setPassword("password");
        request.setPhone("0812345678");
        request.setSalary(BigDecimal.valueOf(20000));
        when(userRepository.checkUserExist(anyString())).thenReturn(true);
        try {
            userService.regisUser(request);
        } catch (CommonException e) {
            assertEquals("ER002", e.getCode());
            throw e;
        }
    }

    @Test(expected = CommonException.class)
    public void regisUserSaveFail() {
        RegisUserRequest request = new RegisUserRequest();
        request.setUserName("username");
        request.setPassword("password");
        request.setPhone("0812345678");
        request.setSalary(BigDecimal.valueOf(20000));
        when(userRepository.checkUserExist(anyString())).thenReturn(false);
        when(userRepository.saveUserInfo(any(), anyString(), anyString())).thenReturn(0);
        try {
            userService.regisUser(request);
        } catch (CommonException e) {
            assertEquals("ER004", e.getCode());
            throw e;
        }
    }

    @Test
    public void getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("userName");
        userInfo.setPassword("password");
        userInfo.setAddress("address");
        userInfo.setSalary(BigDecimal.valueOf(20000));
        userInfo.setPhone("phone");
        userInfo.setEmail("email");
        userInfo.setFirstName("firstName");
        userInfo.setLastName("lastName");
        userInfo.setReferenceCode("referenceCode");
        userInfo.setMemberType("memberType");
        when(userRepository.getUserInfo(anyString())).thenReturn(userInfo);
        GetUserInfoResponse response = userService.getUserInfo("userName");
        assertEquals("userName", response.getUserName());
        assertEquals("address", response.getAddress());
        assertEquals(BigDecimal.valueOf(20000), response.getSalary());
        assertEquals("phone", response.getPhone());
        assertEquals("email", response.getEmail());
        assertEquals("firstName", response.getFirstName());
        assertEquals("lastName", response.getLastName());
        assertEquals("referenceCode", response.getReferenceCode());
        assertEquals("memberType", response.getMemberType());
    }
}