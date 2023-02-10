package com.backend.demo.registrationservice.repository;

import com.backend.demo.registrationservice.model.request.RegisUserRequest;
import com.backend.demo.registrationservice.model.user.UserInfo;
import com.backend.demo.registrationservice.repository.mapper.UserInfoMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserInfo getUserInfo (String userName){
        String query = "SELECT * FROM `user` WHERE userName = ?";
        return jdbcTemplate.queryForObject(query, new UserInfoMapper(), userName);
    }

    public boolean checkUserExist(String userName) {
        String query = "SELECT EXISTS(SELECT COUNT(1) FROM `user` WHERE userName = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, boolean.class, userName));
    }

    public int saveUserInfo (RegisUserRequest request, String referenceCode, String memberType){
        String sql = "INSERT INTO `user` (`userName`, `password`, `address`, `salary`, `phone`, `email`, `firstName`, `lastName`, `referenceCode`, `memberType`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                request.getUserName(),
                request.getPassword(),
                request.getAddress(),
                request.getSalary(),
                request.getPhone(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                referenceCode,
                memberType
        );
    }
}
