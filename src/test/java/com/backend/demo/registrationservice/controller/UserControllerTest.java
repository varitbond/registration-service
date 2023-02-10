package com.backend.demo.registrationservice.controller;


import com.backend.demo.registrationservice.config.security.JwtTokenProvider;
import com.backend.demo.registrationservice.model.response.GetUserInfoResponse;
import com.backend.demo.registrationservice.model.response.LoginResponse;
import com.backend.demo.registrationservice.model.response.RegisUserResponse;
import com.backend.demo.registrationservice.model.user.UserInfo;
import com.backend.demo.registrationservice.repository.UserRepository;
import com.backend.demo.registrationservice.service.UserService;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResourceLoader resourceLoader;
    @MockBean
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void regisUser() throws Exception {
        String url = UriComponentsBuilder.fromPath("/user/register")
                .build().toUriString();
        when(userService.regisUser(any())).thenReturn(new RegisUserResponse("test", "Gold", "202302095565"));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(this.readSourceToString("classpath:RegisRequest.json"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.data.userName", is("test")))
                .andExpect(jsonPath("$.data.memberType", is("Gold")))
                .andExpect(jsonPath("$.data.referenceCode", is("202302095565")));
    }

    @Test
    public void login() throws Exception {
        String url = UriComponentsBuilder.fromPath("/user/login")
                .build().toUriString();
        when(userService.login(any())).thenReturn(new LoginResponse("test123456789"));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(this.readSourceToString("classpath:LoginRequest.json"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.data.token", is("test123456789")));
    }

    @Test
    public void getUserInfo() throws Exception {
        String url = UriComponentsBuilder.fromPath("/user/info")
                .build().toUriString();

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
        when(userService.getUserInfo(anyString())).thenReturn(new GetUserInfoResponse(userInfo));

        String tokenString = jwtTokenProvider.createToken("userName");
        when(userRepository.getUserInfo(anyString())).thenReturn(userInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .param("userName", "userName")
                        .header("Authorization", tokenString)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.data.userName", is("userName")))
                .andExpect(jsonPath("$.data.address", is("address")))
                .andExpect(jsonPath("$.data.salary", is(20000)))
                .andExpect(jsonPath("$.data.phone", is("phone")))
                .andExpect(jsonPath("$.data.email", is("email")))
                .andExpect(jsonPath("$.data.firstName", is("firstName")))
                .andExpect(jsonPath("$.data.lastName", is("lastName")))
                .andExpect(jsonPath("$.data.memberType", is("memberType")))
                .andExpect(jsonPath("$.data.referenceCode", is("referenceCode")));
    }

    @Test
    public void getUserInfoWrongToken() throws Exception {
        String url = UriComponentsBuilder.fromPath("/user/info")
                .build().toUriString();

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .param("userName", "userName")
                        .header("Authorization", "test")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());


    }

    @Test
    public void getUserInfoUnAuth() throws Exception {
        String url = UriComponentsBuilder.fromPath("/user/info")
                .build().toUriString();

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .param("userName", "userName")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    private String readSourceToString(String source) throws IOException {
        Resource resource = resourceLoader.getResource(source);
        String text = null;
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            text = CharStreams.toString(reader);
        }
        return text;
    }
}